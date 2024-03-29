package com.huida.learn.saga.reverse.handler;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.http.ResBodyBO;
import com.huida.learn.saga.journal.handler.Handler;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 事务管理器
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component("transMgtHandler")
public class TransMgtHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void handle(Object input) {
        log.info("TransMgtHandler start");
        try {
            nextHandler.handle(input);
            ResBodyBO resBodyBO = (ResBodyBO)ControllerContext.getContext().getOutput();
            if (!StatusEnum.SUCCESS.getCode().equals(resBodyBO.getSysTxCode())) {
                //冲正

            }

        } catch (Exception e) {
            log.error("TransMgtHandler error", e);
        }

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
