package com.huida.learn.saga.composite;

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


}
