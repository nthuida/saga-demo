package com.huida.learn.saga.journal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InBoundJournal {
    private String sysEvtTraceId;

    private String txTypeInd;

    private String sysTxCode;

    private Date sysReqTime;

    private Date sysRespTime;

    private String sysTxStatus;

    private String rvrsStcd;

    private String sysRespCode;

    private String sysRespDesc;

    private Date txnDt;

    private String txnTm;

    private String inptMsg;

    private String otptMsg;

    private Date lastModTm;

    private Date rcrdRgtm;

}