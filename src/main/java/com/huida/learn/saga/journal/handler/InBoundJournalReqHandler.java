package com.huida.learn.saga.journal.handler;

import com.huida.learn.saga.journal.service.impl.InBoundJournalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接入请求处理栈
 * @author: huida
 * @date: 2024/3/18
 **/
@Component("inBoundJournalReqHandler")
public class InBoundJournalReqHandler implements Handler{

    private Handler nextHandler;

    @Autowired
    private InBoundJournalHandler inBoundJournalHandler;


    @Override
    public void handle(Object input) {
        //处理逻辑
        inBoundJournalHandler.beforeProcess();
        System.out.println("InBoundJournalReqHandler before");
        // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
        if (nextHandler != null) {
            nextHandler.handle(input);
        }
        System.out.println("InBoundJournalReqHandler after");
    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
