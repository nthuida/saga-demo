package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.enums.TypeEnum;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.http.ResponseBody;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.util.ControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class InBoundJournalHandler {

    @Autowired
    private InboundJournalService inboundJournalService;

    public void normalBeforeProcess() {
        RequestMsg requestMsg = (RequestMsg) ControllerContext.getContext().getInput();
        InBoundJournal inBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(requestMsg.getSysEvtTraceId())
                .sysTxCode(requestMsg.getSysTxCode())
                .txTypeInd(requestMsg.getTxTypeInd())
                .sysTxStatus(StatusEnum.DOING.getCode())
                .rvrsStcd(StatusEnum.UNDO.getCode())
                .sysReqTime(new Date())
                .rcrdRgtm(new Date()).build();
        inboundJournalService.insert(inBoundJournal);

    }

    public void normalAfterProcess() {
        ResponseBody res = (ResponseBody) ControllerContext.getContext().getOutput();
        InBoundJournal inBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(res.getSysEvtTraceId())
                .sysTxCode(res.getSysTxCode())
                .txTypeInd(res.getTxTypeInd())
                .sysTxStatus(res.getSysTxStatus())
                .sysRespCode(res.getSysRespCode())
                .sysRespDesc(res.getSysRespDesc())
                .rvrsStcd(res.getRvrsStcd())
                .sysRespTime(new Date()).build();
        inboundJournalService.updateByPrimaryKey(inBoundJournal);

    }


    public void reverseBeforeProcess() {
        RequestMsg requestMsg = (RequestMsg) ControllerContext.getContext().getInput();
        InBoundJournal reverseInBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(requestMsg.getSysEvtTraceId())
                .sysTxCode(requestMsg.getSysTxCode())
                .txTypeInd(requestMsg.getTxTypeInd())
                .sysTxStatus(StatusEnum.DOING.getCode())
                .rvrsStcd(StatusEnum.UNDO.getCode())
                .sysReqTime(new Date())
                .rcrdRgtm(new Date()).build();
        inboundJournalService.insert(reverseInBoundJournal);

        InBoundJournal updateJournal = InBoundJournal.builder()
                .sysEvtTraceId(requestMsg.getSysEvtTraceId())
                .sysTxCode(requestMsg.getSysTxCode())
                .txTypeInd(TypeEnum.NORMAL.getCode())
                .rvrsStcd(StatusEnum.DOING.getCode()).build();
        //更新原交易的冲正状态为执行中
        inboundJournalService.updateByPrimaryKey(updateJournal);


    }


    public void reverseAfterProcess(ReverseResult result) {
        ResponseBody res = (ResponseBody) ControllerContext.getContext().getOutput();
        InBoundJournal reverseInBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(res.getSysEvtTraceId())
                .sysTxCode(res.getSysTxCode())
                .txTypeInd(res.getTxTypeInd())
                .sysTxStatus(result.getStatus())
                .sysRespCode(result.getRespCode())
                .sysRespDesc(result.getRespDesc())
                .sysRespTime(new Date()).build();
        inboundJournalService.updateByPrimaryKey(reverseInBoundJournal);


        InBoundJournal updateJournal = InBoundJournal.builder()
                .sysEvtTraceId(res.getSysEvtTraceId())
                .sysTxCode(res.getSysTxCode())
                .txTypeInd(TypeEnum.NORMAL.getCode()).build();
        if (result.getStatus().equals(StatusEnum.SUCCESS.getCode())) {
            //更新原交易状态为失败
            updateJournal.setSysTxStatus(StatusEnum.FAIL.getCode());
            updateJournal.setRvrsStcd(StatusEnum.SUCCESS.getCode());
        } else {
            updateJournal.setRvrsStcd(StatusEnum.FAIL.getCode());
        }

        //更新原交易的冲正状态
        inboundJournalService.updateByPrimaryKey(updateJournal);

    }
}
