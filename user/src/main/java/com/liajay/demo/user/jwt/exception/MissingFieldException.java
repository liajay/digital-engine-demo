package com.liajay.demo.user.jwt.exception;

import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.user.jwt.JwtToken;

public class MissingFieldException extends JwtVerifyException{
    private final String fieldName;

    public MissingFieldException(JwtToken jwtToken,String fieldName) {
        super(jwtToken, ErrorCode.USER_AUTHENTICATION_FAILED, "jwt缺少字段: " + fieldName);
        this.fieldName = fieldName;
    }
}
