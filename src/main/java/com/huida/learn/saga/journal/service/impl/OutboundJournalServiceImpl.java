package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.enums.TypeEnum;
import com.huida.learn.saga.journal.mapper.OutBoundJournalMapper;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.util.ControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class OutboundJournalServiceImpl implements OutboundJournalService {

    @Autowired
    private OutBoundJournalMapper outboundJournalMapper;


    @Override
    public OutBoundJournal getOutBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode) {
        return outboundJournalMapper.selectByPrimaryKey(sysEvtTraceId, txTypeInd, sysTxCode);
    }

    /**
     * 外呼前处理
     */
    @Override
    public void normalBeforeProcess(){
        OutBoundJournal journal = (OutBoundJournal)ControllerContext.getContext().getOutCallMsg();
        journal.setRcrdRgtm(new Date());
        journal.setSysReqTime(new Date());
        journal.setRvrsStcd(StatusEnum.UNDO.getCode());
        journal.setSysTxStatus(StatusEnum.DOING.getCode());
        outboundJournalMapper.insertSelective(journal);

    }

    /**
     * 冲正外呼前处理
     * @param outBoundJournal
     */
    @Override
    public void reverseBeforeProcess(OutBoundJournal outBoundJournal){
        OutBoundJournal journal = new OutBoundJournal();
        journal.setSysEvtTraceId(outBoundJournal.getSysEvtTraceId());
        journal.setSysTxCode(outBoundJournal.getSysTxCode());
        journal.setTxTypeInd(TypeEnum.REVERSE.getCode());
        journal.setStepSn(outBoundJournal.getStepSn());
        journal.setSysTxStatus(StatusEnum.DOING.getCode());
        journal.setRvrsStcd(StatusEnum.UNDO.getCode());
        journal.setRcrdRgtm(new Date());
        journal.setSysReqTime(new Date());
        outboundJournalMapper.insertSelective(journal);
    }

    @Override
    public void normalAfterProcess(){
        OutBoundJournal journal = (OutBoundJournal)ControllerContext.getContext().getOutCallMsg();
        journal.setSysRespTime(new Date());
        outboundJournalMapper.updateByPrimaryKeySelective(journal);
    }


    @Override
    public void reverseAfterProcess(OutBoundJournal outBoundJournal, ReverseResult result){
        OutBoundJournal journal = new OutBoundJournal();
        journal.setSysEvtTraceId(outBoundJournal.getSysEvtTraceId());
        journal.setSysTxCode(outBoundJournal.getSysTxCode());
        journal.setTxTypeInd(TypeEnum.REVERSE.getCode());
        journal.setSysTxStatus(result.getStatus());
        journal.setSysRespCode(result.getRespCode());
        journal.setSysRespDesc(result.getRespDesc());
        journal.setSysRespTime(new Date());
        outboundJournalMapper.updateByPrimaryKeySelective(journal);
        if (StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
            //冲正成功，更新原交易的状态
            OutBoundJournal originJournal = new OutBoundJournal();
            originJournal.setSysEvtTraceId(outBoundJournal.getSysEvtTraceId());
            originJournal.setSysTxCode(outBoundJournal.getSysTxCode());
            originJournal.setTxTypeInd(TypeEnum.NORMAL.getCode());
            originJournal.setRvrsStcd(StatusEnum.SUCCESS.getCode());
            originJournal.setSysTxStatus(StatusEnum.FAIL.getCode());
            outboundJournalMapper.updateByPrimaryKeySelective(originJournal);
        }
    }

    /**
     * 获取待冲正的交易流水
     * @param sysEvtTraceId
     * @param txTypeInd
     * @return
     */
    @Override
    public List<OutBoundJournal> getReverseList(String sysEvtTraceId, String txTypeInd) {
        return outboundJournalMapper.getReverseList(sysEvtTraceId, txTypeInd);
    }


}
