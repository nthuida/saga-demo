package com.huida.learn.saga.bo;

/**
 * 响应的body
 * @author: huida
 * @date: 2024/3/27
 **/
public class ResBodyBO {

    private String status;

    private String globalTransactionId;

    public ResBodyBO(String status,String globalTransactionId) {
        this.status = status;
        this.globalTransactionId = globalTransactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGlobalTransactionId() {
        return globalTransactionId;
    }

    public void setGlobalTransactionId(String globalTransactionId) {
        this.globalTransactionId = globalTransactionId;
    }
}
