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

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
