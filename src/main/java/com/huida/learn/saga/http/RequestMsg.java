package com.huida.learn.saga.http;

/**
 * 请求报文
 * @author: huida
 * @date: 2024/3/27
 **/


public class RequestMsg {

    /**
     * 全局事务id
     */
    private String globalTransactionId;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 服务种类
     */
    private String type;

    public String getGlobalTransactionId() {
        return globalTransactionId;
    }

    public void setGlobalTransactionId(String globalTransactionId) {
        this.globalTransactionId = globalTransactionId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
