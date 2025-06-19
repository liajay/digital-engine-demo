package com.liajay.demo.user.exception;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;

public class AuthenticationException extends BizException {

    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
