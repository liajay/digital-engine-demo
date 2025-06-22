package com.liajay.demo.user.model.response;

public class LoginResponse {
    private String token;
    private long expireTime;
    private long userId;


    public LoginResponse(long userId, long expireTime, String token) {
        this.expireTime = expireTime;
        this.token = token;
        this.userId = userId;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
