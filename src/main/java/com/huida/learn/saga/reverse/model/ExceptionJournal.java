package com.huida.learn.saga.reverse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionJournal {
    private String sysEvtTraceId;

    private String txTypeInd;

    private String sysTxCode;

    private String rvrsStcd;

    private String revRespCode;

    private Integer rvrsCnt;

    private Date nxtRvrsTmc;

    private Date txnDt;

    private String txnTm;

    private Date lastModTm;

    private Date rcrdRgtm;

}