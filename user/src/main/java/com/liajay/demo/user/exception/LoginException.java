package com.liajay.demo.user.exception;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.model.dto.UserInfo;
import com.liajay.demo.user.model.entity.User;
import jakarta.annotation.Nonnull;

public class LoginException extends BizException {

    public LoginException(@Nonnull ErrorCode errorCode) {
        super(errorCode, "登录失败");
    }

}
