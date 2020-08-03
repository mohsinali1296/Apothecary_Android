package com.example.apothecary.views.view.profile;

import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.User;

import java.util.List;

import okhttp3.RequestBody;

public interface ProfileView {

    void showProgressLoading();
    void hideProgressLoading();
    void setUserProfile(AppUser user);
    void setUpdatedAddress(AppUser user);
    void setUpdatedPassword(AppUser user);
    void onErrorLoading(String message);
}
