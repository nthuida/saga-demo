package com.huida.learn.saga.reverse.model;

import java.util.Date;

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

    public String getSysEvtTraceId() {
        return sysEvtTraceId;
    }

    public void setSysEvtTraceId(String sysEvtTraceId) {
        this.sysEvtTraceId = sysEvtTraceId;
    }

    public String getTxTypeInd() {
        return txTypeInd;
    }

    public void setTxTypeInd(String txTypeInd) {
        this.txTypeInd = txTypeInd;
    }

    public String getSysTxCode() {
        return sysTxCode;
    }

    public void setSysTxCode(String sysTxCode) {
        this.sysTxCode = sysTxCode;
    }

    public String getRvrsStcd() {
        return rvrsStcd;
    }

    public void setRvrsStcd(String rvrsStcd) {
        this.rvrsStcd = rvrsStcd;
    }

    public String getRevRespCode() {
        return revRespCode;
    }

    public void setRevRespCode(String revRespCode) {
        this.revRespCode = revRespCode;
    }

    public Integer getRvrsCnt() {
        return rvrsCnt;
    }

    public void setRvrsCnt(Integer rvrsCnt) {
        this.rvrsCnt = rvrsCnt;
    }

    public Date getNxtRvrsTmc() {
        return nxtRvrsTmc;
    }

    public void setNxtRvrsTmc(Date nxtRvrsTmc) {
        this.nxtRvrsTmc = nxtRvrsTmc;
    }

    public Date getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(Date txnDt) {
        this.txnDt = txnDt;
    }

    public String getTxnTm() {
        return txnTm;
    }

    public void setTxnTm(String txnTm) {
        this.txnTm = txnTm;
    }

    public Date getLastModTm() {
        return lastModTm;
    }

    public void setLastModTm(Date lastModTm) {
        this.lastModTm = lastModTm;
    }

    public Date getRcrdRgtm() {
        return rcrdRgtm;
    }

    public void setRcrdRgtm(Date rcrdRgtm) {
        this.rcrdRgtm = rcrdRgtm;
    }
}