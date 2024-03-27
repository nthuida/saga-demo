package com.huida.learn.saga.reverse.handler;

import com.huida.learn.saga.journal.handler.Handler;
import org.springframework.stereotype.Component;

/**
 * 事务管理器
 * @author: huida
 * @date: 2024/3/18
 **/
@Component("transMgtHandler")
public class TransMgtHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void handle(Object input) {
        //处理逻辑
        System.out.println("TransMgtHandler before");
        // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
        if (nextHandler != null) {
           nextHandler.handle(input);
        }
        System.out.println("TransMgtHandler after");
    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
