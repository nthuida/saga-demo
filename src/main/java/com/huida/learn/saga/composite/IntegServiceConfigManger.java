package com.huida.learn.saga.composite;

import com.huida.learn.saga.model.actor.unit.DcpIntegService;

/**
 * @author: huida
 * @date: 2024/3/19
 **/
public class IntegServiceConfigManger {

    private IntegServiceConfigRegistry integServiceConfigRegistry;

    public DcpIntegService getService(String id) {
        return integServiceConfigRegistry.getService(id);
    }


    public void setIntegServiceConfigRegistry(IntegServiceConfigRegistry integServiceConfigRegistry) {
        this.integServiceConfigRegistry = integServiceConfigRegistry;
    }
}
