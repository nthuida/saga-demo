package com.huida.learn.saga.journal.handler;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
public interface Handler {

    void handle(Object input);

    void setNextHandler(Handler nextHandler);
}
