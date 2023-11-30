package com.tech.skill.normal.constant;

public enum GlobalErrorCode implements IErrorCode{
    //业务错误返回1XX，参数错误返回2XX
    Fail(-1,"failed"),
    ParamEmpty(200,"param can not be empty");


    private Integer code;
    private String msg;

    GlobalErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code ;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
