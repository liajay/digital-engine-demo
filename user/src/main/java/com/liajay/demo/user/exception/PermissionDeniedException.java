package com.liajay.demo.user.exception;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;

public class PermissionDeniedException extends BizException {
    public PermissionDeniedException() {
        super(ErrorCode.USER_PERMISSION_DENIED, "权限不足");
    }
}
