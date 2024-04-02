package com.huida.learn.saga.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应的body
 * @author: huida
 * @date: 2024/3/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

    private String sysEvtTraceId;

    private String txTypeInd;

    private String sysTxCode;

    private String sysTxStatus;

    private String rvrsStcd;

    private String sysRespCode;

    private String sysRespDesc;




}
