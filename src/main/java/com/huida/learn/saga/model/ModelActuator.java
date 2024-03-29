package com.huida.learn.saga.model;


import com.huida.learn.saga.http.ResBodyBO;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.model.actor.unit.DcpIntegService;
import org.springframework.stereotype.Component;

/**
 * 模型执行类
 * @author: huida
 * @date: 2024/3/18
 **/
@Component
public class ModelActuator {

    public Object execute(RequestMsg request, DcpIntegService dcpIntegService){
        // 执行模型
        return new ResBodyBO("00",request.getGlobalTransactionId());
    }
}
