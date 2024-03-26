package com.huida.learn.saga.composite;

import com.huida.learn.saga.model.actor.unit.DcpIntegService;

/**
 * 组合交易配置注册
 * @author: huida
 * @date: 2024/3/19
 **/
public class IntegServiceConfigRegistry {

    public DcpIntegService getService(String id) {
        return (DcpIntegService) new Object();
     }
}
