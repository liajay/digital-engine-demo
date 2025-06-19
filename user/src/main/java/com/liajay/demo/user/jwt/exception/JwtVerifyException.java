package com.liajay.demo.user.jwt.exception;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.user.jwt.JwtToken;

public class JwtVerifyException extends BizException {
    private final JwtToken jwtToken;
    public JwtVerifyException(JwtToken jwtToken, ErrorCode errorCode, String message){
        super(errorCode, message);
        this.jwtToken = jwtToken;
    }

    public JwtVerifyException(JwtToken jwtToken) {
        super(ErrorCode.USER_AUTHENTICATION_FAILED, "jwt校验失败");
        this.jwtToken = jwtToken;
    }
}
