package com.huida.learn.saga.reverse.handler;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.http.ResponseBody;
import com.huida.learn.saga.journal.handler.Handler;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.reverse.service.ReverseService;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 事务管理器
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component("transMgtHandler")
public class TransMgtHandler implements Handler {

    private Handler nextHandler;

    @Autowired
    private ReverseService reverseService;

    @Autowired
    private InboundJournalService  inboundJournalService;

    @Override
    public void handle(Object input) {
        log.info("TransMgtHandler start");
        try {
            nextHandler.handle(input);
            ResponseBody responseBody = (ResponseBody)ControllerContext.getContext().getOutput();
            if (!StatusEnum.SUCCESS.getCode().equals(responseBody.getSysTxStatus())) {
                //冲正
                InBoundJournal journal = inboundJournalService.getJournalByPrimaryKey(responseBody.getSysEvtTraceId(), responseBody.getTxTypeInd(), responseBody.getSysTxCode());
                if (journal != null) {
                    try {
                        ReverseResult result = reverseService.doInnerReverse(journal);
                        if (!ObjectUtils.isEmpty(result)) {
                            if (StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
                                //冲正成功
                                responseBody.setRvrsStcd(result.getStatus());
                                responseBody.setSysTxStatus(StatusEnum.FAIL.getCode());

                            } else {
                                //冲正失败
                                responseBody.setRvrsStcd(result.getStatus());
                                responseBody.setSysTxStatus(StatusEnum.UNKNOWN.getCode());
                            }
                        }
                    } catch (Exception e) {
                        //冲正异常
                        responseBody.setRvrsStcd(StatusEnum.UNKNOWN.getCode());
                        responseBody.setSysTxStatus(StatusEnum.UNKNOWN.getCode());
                        log.error("TransMgtHandler doInnerReverse error", e);
                    } finally {
                        ControllerContext.getContext().setOutput(responseBody);
                    }
                }
            }

        } catch (Exception e) {
            log.error("TransMgtHandler error", e);
        }

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
