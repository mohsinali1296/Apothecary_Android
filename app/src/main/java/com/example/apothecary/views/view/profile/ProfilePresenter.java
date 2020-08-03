package com.example.apothecary.views.view.profile;

import android.net.Uri;
import android.os.FileUtils;

import com.example.apothecary.Utils;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.User;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {
    ProfileView view;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    public void Upload_ProfileImage(int userId,  String photo){

        view.showProgressLoading();

        Call<AppUser> profileCall = Utils.getApothecaryApi().upload_Profile_Image(userId, photo);

        profileCall.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                view.hideProgressLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUserProfile(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                view.hideProgressLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

    public void Update_Address(int userId,  User user){

        view.showProgressLoading();

        Call<AppUser> checkoutCall = Utils.getApothecaryApi().UpdateAddress(userId,user);

        checkoutCall.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                view.hideProgressLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUpdatedAddress(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                view.hideProgressLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

    public void Update_Password(int userId,  String password){

        view.showProgressLoading();

        Call<AppUser> checkoutCall = Utils.getApothecaryApi().UpdatePassword(userId,password);

        checkoutCall.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                view.hideProgressLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUpdatedPassword(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                view.hideProgressLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

}
