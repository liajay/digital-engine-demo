package com.liajay.demo.user.service;


import com.liajay.demo.user.model.UserInfo;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import org.springframework.stereotype.Service;

public interface UserService {
    public void register(User user);

    public LoginResponse login(User user);

    public UserInfo findUserInfo (long userId);

    public void updateUserInfo(UserInfo userInfo);

    public void resetPassword(String password);

}
