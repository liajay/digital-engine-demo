package com.liajay.demo.user.exception;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;

public class RegisterException extends BizException {
    public RegisterException(ErrorCode errorCode) {
        super(errorCode, "注册失败");
    }
}
