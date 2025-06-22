package com.liajay.demo.common.exception;

import com.liajay.demo.common.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<?> BizException(BizException e){
        return ApiResponse.error(e.getMessage(), e.getErrorCode(), e.getContext());
    }

    //TODO: 改下响应
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<?> SystemException(SystemException e){
        return ApiResponse.error(e.getMessage(), e.getErrorCode(), e.getContext());
    }
}
