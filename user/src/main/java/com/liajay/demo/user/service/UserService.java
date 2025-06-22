package com.liajay.demo.user.service;


import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.common.model.dto.UserInfo;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import com.liajay.demo.user.model.response.UpdateUserInfoResponse;

public interface UserService {
    public void register(User user);

    public LoginResponse login(User user);

    public UserInfo findUserInfo (long userId);

    public UpdateUserInfoResponse updateUserInfo(UserInfo userInfo);

    public void resetPassword(String password, long targetId);

}
