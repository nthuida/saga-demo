package com.huida.learn.saga.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 模型步骤
 * @author: huida
 * @date: 2024/4/2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step {

    /**
     * 原子服务名称
     */
    private String service;
    /**
     * 输入参数
     */
    private Map<String, String> args;
    /**
     * 补偿服务名称
     */
    private String compensate;
    /**
     * 条件
     */
    private String condition;

}
