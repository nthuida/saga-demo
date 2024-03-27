package com.huida.learn.saga.composite;

import com.huida.learn.saga.model.actor.unit.DcpIntegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: huida
 * @date: 2024/3/19
 **/
@Component
public class IntegServiceConfigManger {

    @Autowired
    private IntegServiceConfigRegistry integServiceConfigRegistry;

    public DcpIntegService getService(String id) {
        return integServiceConfigRegistry.getService(id);
    }

}
