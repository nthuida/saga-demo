package com.huida.learn.saga.enums;

/**
 * 交易状态枚举类
 * @author: huida
 * @date: 2024/3/27
 **/
public enum StatusEnum {

    SUCCESS("00", "成功"),
    FAIL("01", "失败"),
    UNKNOWN("02", "未知"),
    UNDO("03", "未执行"),
    DOING("04", "执行中"),
    SEQUENCE_ERROR("05", "时序异常");


    private String code;
    private String desc;

    StatusEnum(String code, String desc) {
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
