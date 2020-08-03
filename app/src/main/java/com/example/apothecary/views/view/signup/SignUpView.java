package com.example.apothecary.views.view.signup;

import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterResponse;
import com.example.apothecary.models.user.RegisterationResponse;

public interface SignUpView {
    void showLoading();
    void hideLoading();
    void onErrorLoading(String message);
    void setRegisterdUser(RegisterationResponse registerationResponse);
}
