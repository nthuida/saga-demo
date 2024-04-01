package com.huida.learn.saga.journal.handler;

import com.huida.learn.saga.http.ResBodyBO;
import com.huida.learn.saga.composite.IntegCompositeService;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模型处理栈
 * @author: huida
 * @date: 2024/3/18
 **/
@Slf4j
@Component("modelActuatorHandler")
public class ModelActuatorHandler implements Handler{

    private Handler nextHandler;

    @Autowired
    private IntegCompositeService integCompositeService;

    @Override
    public void handle(Object input) {
        log.info("ModelActuatorHandler start");
        try {
            ResBodyBO output = (ResBodyBO)integCompositeService.execute((RequestMsg)input);
            ControllerContext.getContext().setOutput(output);
            // 如果需要传递给下一个处理节点，调用下一个处理节点的 handleRequest() 方法
            if (nextHandler != null) {
                nextHandler.handle(input);
            }
        } catch (Exception e) {
            log.error("ModelActuatorHandler error", e);
        }

    }

    @Override
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


}
