package com.liajay.demo.user.model.response;

import com.liajay.demo.common.model.dto.UserInfo;

public class UpdateUserInfoResponse {
    private UserInfo oldUserInfo;

    public UpdateUserInfoResponse(UserInfo oldUserInfo) {
        this.oldUserInfo = oldUserInfo;
    }

    public UserInfo getOldUserInfo() {
        return oldUserInfo;
    }

    public void setOldUserInfo(UserInfo oldUserInfo) {
        this.oldUserInfo = oldUserInfo;
    }
}
