package com.huida.learn.saga.enums;

/**
 * 交易类型枚举类
 * @author: huida
 * @date: 2024/3/29
 **/
public enum TypeEnum {

    NORMAL("0", "正交易"),
    INNER_REVERSE("1", "内部冲正交易"),
    OUT_REVERSE("2", "外部冲正交易");


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
