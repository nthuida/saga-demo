package com.huida.learn.saga.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求报文
 * @author: huida
 * @date: 2024/3/27
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMsg {

    /**
     * 全局事务ID
     */
    private String sysEvtTraceId;

    /**
     * 交易类型
     */
    private String txTypeInd;

    /**
     * 交易码
     */
    private String sysTxCode;

}
