package com.huida.learn.saga.journal.handler;
import com.huida.learn.saga.journal.handler.Handler;
import org.springframework.stereotype.Component;

/**
 * @author: huida
 * @date: 2024/3/26
 **/
@Component
public class Invoker {

    private Handler firstHandler;

    public Invoker(Handler firstHandler) {
        this.firstHandler = firstHandler;
    }

    public void invoke(Object request) {
        // 调用责任链的第一个处理器处理请求
        firstHandler.handle(request);
    }
}
