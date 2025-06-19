package com.liajay.demo.common.exception;


public class SystemException extends BaseException {

    public SystemException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, errorCode.getMessage(), cause);
    }

    public SystemException(String message, Throwable cause) {
        super(ErrorCode.UNKNOWN_ERROR, message, cause);
    }
}
