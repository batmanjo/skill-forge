package com.tech.skill.forge.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tech.skill.forge.constant.IErrorCode;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseVO<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static final Integer SUCCESS_CODE = 1;
    public static final String SUCCESS_MSG = "success";



    public ResponseVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVO() {

    }

    public static <T> ResponseVO<T> fail(IErrorCode errorCode, T t) {
        return new ResponseVO<>(errorCode.getCode(),errorCode.getMsg(),t);
    }

    public static <T> ResponseVO<T> fail(IErrorCode errorCode) {
        return fail(errorCode,null);
    }


    public static <T> ResponseVO<T> success() {
        ResponseVO responseVO = new ResponseVO<>();
        return responseVO.whenSuccess(null);
    }

    public static <T> ResponseVO<T> success(T t) {
        ResponseVO responseVO = new ResponseVO<>();
        return responseVO.whenSuccess(t);
    }

    public  ResponseVO<T> whenSuccess(T t) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
        this.data = t;
        return this;
    }



}
