package com.liajay.demo.common.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

public sealed abstract class UserOperationLog permits
        UserOperationLog.Register,
        UserOperationLog.Login,
        UserOperationLog.UpdateUser,
        UserOperationLog.ResetPassword
{

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    private long userId;

    private String ip;

    private String detail;

    private LocalDateTime timeStamp;

    private UserOperationLog(long userId, String ip) {
        this.ip = ip;
        this.timeStamp = LocalDateTime.now();
        this.userId = userId;
    }

    public abstract String getAction();

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public final static class Register extends UserOperationLog {
        public Register(long userId, String ip, UserInfo newUser){
            super(userId, ip);
            try {
                setDetail(objectMapper.writeValueAsString(newUser));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getAction() {
            return "register";
        }
    }

    public final static class Login extends UserOperationLog {
        public Login(long userId, String ip) {
            super(userId, ip);
        }

        @Override
        public String getAction() {
            return "login";
        }

    }

    public final static class UpdateUser extends UserOperationLog {
        public UpdateUser(long userId, String ip, UserInfo newUser, UserInfo oldUser) {
            super(userId, ip);

            try {
                setDetail(objectMapper.writeValueAsString(Map.of("new", newUser, "old", oldUser)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getAction() {
            return "update_user";
        }
    }

    public final static class ResetPassword extends UserOperationLog {
        public ResetPassword(long userId, String ip, String newPwd, String oldPwd) {
            super(userId, ip);
            try {
                setDetail(objectMapper.writeValueAsString(Map.of(
                        "field", "password",
                        "old", oldPwd,
                        "new", newPwd
                )));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getAction() {
            return "reset_password";
        }
    }
}
