package com.huida.learn.saga.composite;

import com.huida.learn.saga.model.ModelActuator;

/**
 * 组合交易入口
 * @author: huida
 * @date: 2024/3/19
 **/
public class IntegCompositeService {

    private IntegServiceConfigManger integServiceConfigManger;

    private ModelActuator modelActuator;

    public Object execute(String request) {

        return modelActuator.execute(request, integServiceConfigManger.getService(""), "serviceid");
    }

    public void setIntegServiceConfigManger(IntegServiceConfigManger integServiceConfigManger) {
        this.integServiceConfigManger = integServiceConfigManger;
    }

    public void setModelActuator(ModelActuator modelActuator) {
        this.modelActuator = modelActuator;
    }
}
