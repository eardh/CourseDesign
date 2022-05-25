package com.huang.handler.ada;

import com.huang.model.enums.ResponseCode;
import com.huang.model.response.ResponseModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author dahuang
 * @date 2021/6/9 20:57
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 默认异常处理
     * @author dahuang
     * @date 2021/6/9 21:00
     * @param exception
     * @return com.dahuang.model.response.ResponseModel
     */
    @ExceptionHandler(Exception.class)
    public ResponseModel defaultErrorHandler(Exception exception) {
        return ResponseModel.fail(ResponseCode.SYSTEM_EXCEPTION);
    }
}
