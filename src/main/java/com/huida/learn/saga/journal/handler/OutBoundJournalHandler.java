package com.huida.learn.saga.journal.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 外呼处理栈
 * @author: huida
 * @date: 2024/3/18
 **/
@Component("outBoundJournalHandler")
@Slf4j
public class OutBoundJournalHandler implements Handler{

    private Handler nextHandler;


    @Override
    public void handle(Object input) {
        //处理逻辑
        beforeProcess();
        // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
        if (nextHandler != null) {
            nextHandler.handle(input);
        }
        afterProcess();

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void beforeProcess() {
        System.out.println("InBoundJournalHandler beforeProcess");
    }

    public void afterProcess() {
        System.out.println("InBoundJournalHandler afterProcess");
    }
}
