package com.huida.learn.saga.reverse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 冲正结果
 * @author: huida
 * @date: 2024/4/1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseResult {

    /**
     * 冲正状态
     */
    private String status;

    /**
     * 冲正响应码
     */
    private String RespCode;

    /**
     * 冲正响应描述
     */
    private String RespDesc;
}
