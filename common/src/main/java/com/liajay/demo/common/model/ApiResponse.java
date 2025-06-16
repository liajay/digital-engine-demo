package com.liajay.demo.common.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.*;
import com.liajay.demo.common.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "success",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApiResponse.Success.class, name = "true"),
        @JsonSubTypes.Type(value = ApiResponse.Failed.class, name = "false")
})
public sealed abstract class ApiResponse<T> permits ApiResponse.Success, ApiResponse.Failed {

    public abstract boolean isSuccess();

    public final static class Success<T> extends ApiResponse<T> {
        private final T data;

        @JsonCreator
        public Success(@JsonProperty("data") T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }

    public final static class Failed<T> extends ApiResponse<T> {
        private final String message;
        private final ErrorCode errorCode;

        @JsonCreator
        public Failed(
                @JsonProperty("message") String message,
                @JsonProperty("errorCode") ErrorCode errorCode
        ) {
            this.message = message;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }

    public static <T> ApiResponse<T> success(T data) {
        return new Success<>(data);
    }

    public static <T> ApiResponse<T> error(String message, ErrorCode errorCode) {
        return new Failed<>(message, errorCode);
    }
}
