package com.huida.learn.saga.http;

/**
 * 响应报文
 * @author: huida
 * @date: 2024/3/27
 **/
public class ResponseMsg<T> {

    public static final int CODE_SUCCESS = 200;

    private Integer code;
    private String message;
    private T data;

    public ResponseMsg(T result) {
        this(CODE_SUCCESS, result, "SUCCESS");
    }

    public ResponseMsg(int code, String message) {
        this(code, null, message);
    }

    public ResponseMsg(int code, T data, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
