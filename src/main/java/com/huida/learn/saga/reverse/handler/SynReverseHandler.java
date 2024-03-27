package com.huida.learn.saga.reverse.handler;

import com.huida.learn.saga.journal.handler.Handler;

/**
 * 外部冲正处理器
 * @author: huida
 * @date: 2024/3/19
 **/
public class SynReverseHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void handle(Object input) {
        Object output = null;
        //处理逻辑
        System.out.println("SynReverseHandler before");
        // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
        if (nextHandler != null) {
            nextHandler.handle(input);
        }
        System.out.println("SynReverseHandler after");
    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
