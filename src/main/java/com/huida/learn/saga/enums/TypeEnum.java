package com.huida.learn.saga.enums;

/**
 * 交易类型枚举类
 * @author: huida
 * @date: 2024/3/29
 **/
public enum TypeEnum {

    NORMAL("0", "正交易"),
    REVERSE("1", "冲正交易");


    private String code;
    private String desc;

    TypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

}
