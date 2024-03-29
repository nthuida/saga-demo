package com.huida.learn.saga.journal.model;

import java.util.Date;

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

    public Date getSysReqTime() {
        return sysReqTime;
    }

    public void setSysReqTime(Date sysReqTime) {
        this.sysReqTime = sysReqTime;
    }

    public Date getSysRespTime() {
        return sysRespTime;
    }

    public void setSysRespTime(Date sysRespTime) {
        this.sysRespTime = sysRespTime;
    }

    public String getSysTxStatus() {
        return sysTxStatus;
    }

    public void setSysTxStatus(String sysTxStatus) {
        this.sysTxStatus = sysTxStatus;
    }

    public String getRvrsStcd() {
        return rvrsStcd;
    }

    public void setRvrsStcd(String rvrsStcd) {
        this.rvrsStcd = rvrsStcd;
    }

    public String getSysRespCode() {
        return sysRespCode;
    }

    public void setSysRespCode(String sysRespCode) {
        this.sysRespCode = sysRespCode;
    }

    public String getSysRespDesc() {
        return sysRespDesc;
    }

    public void setSysRespDesc(String sysRespDesc) {
        this.sysRespDesc = sysRespDesc;
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

    public String getInptMsg() {
        return inptMsg;
    }

    public void setInptMsg(String inptMsg) {
        this.inptMsg = inptMsg;
    }

    public String getOtptMsg() {
        return otptMsg;
    }

    public void setOtptMsg(String otptMsg) {
        this.otptMsg = otptMsg;
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