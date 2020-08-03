package com.example.apothecary.views.view.login;

import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterationResponse;
import com.example.apothecary.models.user.User;

import java.util.List;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void showGoogleLoading();
    void hideGoogleLoading();
    void onErrorLoading(String message);
    void setUserLogin(LoginUserResponse loginUser);
    void setCheckUser(List<User> user);
    void setRegisterdUser(RegisterationResponse registerationResponse);
}
