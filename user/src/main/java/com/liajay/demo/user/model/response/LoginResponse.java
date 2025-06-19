package com.liajay.demo.user.model.response;

public class LoginResponse {
    public String token;
    public long expireTime;

    public LoginResponse(long expireTime, String token) {
        this.expireTime = expireTime;
        this.token = token;
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
}
