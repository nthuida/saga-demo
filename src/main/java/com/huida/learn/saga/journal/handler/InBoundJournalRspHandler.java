package com.huida.learn.saga.journal.handler;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
public class InBoundJournalRspHandler implements Handler {

    private Handler nextHandler;

    private InBoundJournalHandler inBoundJournalHandler;

    @Override
    public void handle(Object input) {
        //处理逻辑
        // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
        if (nextHandler != null) {
            nextHandler.handle(input);
        }
        inBoundJournalHandler.afterProcess();

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
