package com.liajay.demo.common.exception;

public class BizException extends BaseException{
    public BizException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
