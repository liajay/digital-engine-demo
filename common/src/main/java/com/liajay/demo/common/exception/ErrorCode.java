package com.liajay.demo.common.exception;

public enum ErrorCode {
    SUCCESS("00000", "成功"),
    UNKNOWN_ERROR("99999", "未知错误"),
    PARAM_VALIDATION_FAILED("00401", "参数校验失败"),
    SERVICE_UNAVAILABLE("00503", "服务不可用");
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
