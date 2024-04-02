package com.huida.learn.saga.journal.handler;

import com.huida.learn.saga.journal.service.impl.InBoundJournalHandler;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接入响应栈
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component("inBoundJournalRspHandler")
public class InBoundJournalRspHandler implements Handler {

    private Handler nextHandler;

    @Autowired
    private InBoundJournalHandler inBoundJournalHandler;

    @Override
    public void handle(Object input) {
        log.info("InBoundJournalRspHandler start");
        try {
            ControllerContext.getContext().setInput(input);
            nextHandler.handle(input);
        } catch (Exception e){
            log.error("InBoundJournalRspHandler error", e);
        } finally {
            inBoundJournalHandler.normalAfterProcess();
        }

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
