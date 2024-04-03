package com.huida.learn.saga.reverse.task;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.reverse.mapper.ExceptionJournalMapper;
import com.huida.learn.saga.reverse.model.ExceptionJournal;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.reverse.service.ReverseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 异步冲正定时任务
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component
public class AsynReverseTask {

    @Autowired
    private ExceptionJournalMapper exceptionJournalMapper;

    @Autowired
    private ReverseService reverseService;

    @Autowired
    private OutboundJournalService outboundJournalService;

    @Autowired
    private InboundJournalService inboundJournalService;


    /**
     * 定时冲正 间隔1分钟
     */
    @Scheduled(fixedDelay = 60000, initialDelay = 30000)
    public void asynReverse(){
        log.info("异步冲正定时任务 start");
        try {
            List<ExceptionJournal> list = reverseService.getReverseJournal();
            if (!CollectionUtils.isEmpty(list)) {
                for (ExceptionJournal journal : list) {
                    //获取锁执行
                    doReverse(journal);
                }
            }
        } catch (Exception e) {
            log.error("异步冲正定时任务异常", e);
        }

        log.info("异步冲正定时任务 end");
    }

    private void doReverse(ExceptionJournal journal){
        List<OutBoundJournal> list = outboundJournalService.getReverseList(journal.getSysEvtTraceId(), journal.getTxTypeInd());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        journal.setRvrsStcd(StatusEnum.DOING.getCode());
        journal.setLastModTm(new Date());
        journal.setRvrsCnt(journal.getRvrsCnt() + 1);
        //更新异常流水的冲正状态为执行中
        exceptionJournalMapper.updateByPrimaryKeySelective(journal);
        ReverseResult result = new ReverseResult();
        for (OutBoundJournal outBoundJournal : list) {
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
                log.error("AsynReverseTask error", e);
                result.setRespCode("00000002");
                result.setStatus(StatusEnum.UNKNOWN.getCode());
                result.setRespDesc("冲正未知异常");
            }
            outboundJournalService.reverseAfterProcess(outBoundJournal, result);
            if (!StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
                break;
            }
        }

        if (StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
            //全部冲正成功
            journal.setRvrsStcd(StatusEnum.SUCCESS.getCode());
            journal.setRevRespCode(result.getRespCode());
            //更新异常流水的冲正状态为成功
            exceptionJournalMapper.updateByPrimaryKeySelective(journal);
            //更新接入流水状态
            InBoundJournal inBoundJournal = new InBoundJournal();
            inBoundJournal.setSysEvtTraceId(journal.getSysEvtTraceId());
            inBoundJournal.setTxTypeInd(journal.getTxTypeInd());
            inBoundJournal.setSysTxCode(journal.getSysTxCode());
            inBoundJournal.setRvrsStcd(StatusEnum.SUCCESS.getCode());
            inBoundJournal.setSysTxStatus(StatusEnum.FAIL.getCode());
            inboundJournalService.updateByPrimaryKey(inBoundJournal);

        } else {
            //失败
            journal.setRvrsStcd(StatusEnum.FAIL.getCode());
            journal.setRevRespCode(result.getRespCode());
            //更新异常流水的冲正状态为成功
            exceptionJournalMapper.updateByPrimaryKeySelective(journal);

        }
    }
}
