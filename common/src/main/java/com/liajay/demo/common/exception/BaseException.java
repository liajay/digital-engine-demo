package com.liajay.demo.common.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> context = new HashMap<>();

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getContext() {
        return new HashMap<>(context);
    }

    public BaseException addContext(String key, Object value) {
        context.put(key, value);
        return this;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (!context.isEmpty()) {
            message += " [Context: " + context.toString() + "]";
        }
        return message;
    }
}
