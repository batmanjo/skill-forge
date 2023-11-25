package com.tech.skill.forge.handler;

import com.tech.skill.forge.common.ResponseVO;
import com.tech.skill.forge.constant.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseVO<String> methodArgumentNotValidExceptionHandler(Exception e) {
        return ResponseVO.fail(GlobalErrorCode.Fail,e.getMessage());
    }

}
