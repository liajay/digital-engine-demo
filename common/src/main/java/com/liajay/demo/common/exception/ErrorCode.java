package com.liajay.demo.common.exception;

public enum ErrorCode {
    SUCCESS("00000", "成功"),
    UNKNOWN_ERROR("99999", "未知错误"),
    PARAM_VALIDATION_FAILED("00401", "参数校验失败"),
    SERVICE_UNAVAILABLE("00503", "服务不可用"),

    USERNAME_ALREADY_EXISTS("10301", "用户名已存在"),
    USER_NOT_FOUND("10302", "用户不存在"),
    USER_TOKEN_INVALID("10303","用户token失效"),
    USER_AUTHENTICATION_FAILED("10304", "用户认证失败"),
    USER_PERMISSION_DENIED("10305", "用户权限不足");
    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
