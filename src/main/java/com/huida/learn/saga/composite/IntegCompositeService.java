package com.huida.learn.saga.composite;

import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.model.ModelActuator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 组合交易入口
 * @author: huida
 * @date: 2024/3/19
 **/
@Component
public class IntegCompositeService {

    @Autowired
    private ModelActuator modelActuator;

    public Object execute(Object request) {
        RequestMsg requestMsg = (RequestMsg) request;
        return modelActuator.execute(requestMsg);
    }

}
