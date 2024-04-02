package com.huida.learn.saga.reverse.service.impl;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.enums.TypeEnum;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.reverse.mapper.ExceptionJournalMapper;
import com.huida.learn.saga.reverse.model.ExceptionJournal;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.reverse.service.ReverseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Service
public class ReverseServiceImpl implements ReverseService {

    @Autowired
    private OutboundJournalService outboundJournalService;

    @Autowired
    private ExceptionJournalMapper exceptionJournalMapper;


    /**
     * 外部冲正
     * @param journal
     */
    @Override
    public ReverseResult doOutReverse(InBoundJournal journal){
        ReverseResult result = null;
        List<OutBoundJournal> list = outboundJournalService.getReverseList(journal.getSysEvtTraceId(), TypeEnum.NORMAL.getCode());
        if (!CollectionUtils.isEmpty(list)) {
            result = new ReverseResult();
            result.setStatus(StatusEnum.SUCCESS.getCode());
            result.setRespCode("000000000");
            result.setRespDesc("冲正成功");
            for (OutBoundJournal outBoundJournal : list) {
                outboundJournalService.reverseBeforeProcess(outBoundJournal);
                try {
                    //模拟冲正的结果
                    boolean flag = new Random().nextBoolean();
                    if (flag) {
                        result.setStatus(StatusEnum.SUCCESS.getCode());
                        result.setRespCode("00000000");
                        result.setRespDesc("冲正成功");
                    } else {
                        result.setStatus(StatusEnum.FAIL.getCode());
                        result.setRespCode("00000001");
                        result.setRespDesc("冲正失败");
                    }
                } catch (Exception e) {
                    log.error("doInnerReverse error", e);
                    result.setRespCode("00000002");
                    result.setStatus(StatusEnum.UNKNOWN.getCode());
                    result.setRespDesc("冲正未知异常");
                }
                outboundJournalService.reverseAfterProcess(outBoundJournal, result);
                if (!StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
                    break;
                }
            }
            afterProcess(journal, result);
        }

        return result;
    }

    /**
     * 内部冲正
     * @param journal
     */
    @Override
    public ReverseResult doInnerReverse(InBoundJournal journal){
        ReverseResult result = null;
        List<OutBoundJournal> list = outboundJournalService.getReverseList(journal.getSysEvtTraceId(), journal.getTxTypeInd());
        if (!CollectionUtils.isEmpty(list)) {
            result = new ReverseResult();
            beforeProcess(journal);
            result.setStatus(StatusEnum.SUCCESS.getCode());
            result.setRespCode("000000000");
            result.setRespDesc("冲正成功");
            for (OutBoundJournal outBoundJournal : list) {
                outboundJournalService.reverseBeforeProcess(outBoundJournal);
                try {
                    //模拟冲正的结果
                    boolean flag = new Random().nextBoolean();
                    if (flag) {
                        result.setStatus(StatusEnum.SUCCESS.getCode());
                        result.setRespCode("00000000");
                        result.setRespDesc("冲正成功");
                    } else {
                        result.setStatus(StatusEnum.FAIL.getCode());
                        result.setRespCode("00000001");
                        result.setRespDesc("冲正失败");
                    }
                } catch (Exception e) {
                    log.error("doInnerReverse error", e);
                    result.setRespCode("00000002");
                    result.setStatus(StatusEnum.UNKNOWN.getCode());
                    result.setRespDesc("冲正未知异常");
                }
                outboundJournalService.reverseAfterProcess(outBoundJournal, result);
                if (!StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
                    break;
                }
            }
            afterProcess(journal, result);
        }

        return result;
    }

    private void beforeProcess(InBoundJournal journal){
        ExceptionJournal exceptionJournal = ExceptionJournal.builder()
                .sysEvtTraceId(journal.getSysEvtTraceId())
                .sysTxCode(journal.getSysTxCode())
                .txTypeInd(journal.getTxTypeInd())
                .rvrsCnt(1)
                .rvrsStcd(StatusEnum.DOING.getCode())
                .rcrdRgtm(new Date()).build();
        exceptionJournalMapper.insertSelective(exceptionJournal);

    }

    private void afterProcess(InBoundJournal journal, ReverseResult result){
        ExceptionJournal exceptionJournal = ExceptionJournal.builder()
                .sysEvtTraceId(journal.getSysEvtTraceId())
                .sysTxCode(journal.getSysTxCode())
                .txTypeInd(journal.getTxTypeInd())
                .rvrsStcd(result.getStatus())
                .revRespCode(result.getRespCode()).build();
        exceptionJournalMapper.updateByPrimaryKeySelective(exceptionJournal);

    }

}
