package com.huida.learn.saga.journal.handler;

import com.huida.learn.saga.journal.service.impl.InBoundJournalHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接入请求处理栈
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component("inBoundJournalReqHandler")
public class InBoundJournalReqHandler implements Handler{

    private Handler nextHandler;

    @Autowired
    private InBoundJournalHandler inBoundJournalHandler;


    @Override
    public void handle(Object input) {
        log.info("InBoundJournalReqHandler start");
        try {
            inBoundJournalHandler.beforeProcess();
            nextHandler.handle(input);
        } catch (Exception e) {
            log.error("InBoundJournalReqHandler error", e);
        }

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
