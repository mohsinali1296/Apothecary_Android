package com.example.apothecary.views.view.login;

import android.util.Log;
import android.widget.Toast;

import com.example.apothecary.Utils;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.helper.APIError;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterationResponse;
import com.example.apothecary.models.user.User;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    LoginView view;

    public LoginPresenter(LoginView view) { this.view = view; }

    void UserLogin(AppUser user){

        view.showLoading();
        Call<LoginUserResponse> userCall = Utils.getApothecaryApi().Login(user);
        userCall.enqueue(new Callback<LoginUserResponse>() {
            @Override
            public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setUserLogin(response.body());
                }
                else{
                    view.setUserLogin(null);
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                //view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

   public void CheckUser(String email){
        view.showGoogleLoading();
        Call<List<User>> userCall = Utils.getApothecaryApi().checkUser(email);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               view.hideGoogleLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setCheckUser(response.body());
                }
                else{
                    view.setUserLogin(null);
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.hideGoogleLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

   public void Register(AppUser user){
       view.showGoogleLoading();
        Call<RegisterationResponse> userCall = Utils.getApothecaryApi().RegisterWithGoogle(user);
        userCall.enqueue(new Callback<RegisterationResponse>() {
            @Override
            public void onResponse(Call<RegisterationResponse> call, Response<RegisterationResponse> response) {
                view.hideGoogleLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setRegisterdUser(response.body());
                }

            }

            @Override
            public void onFailure(Call<RegisterationResponse> call, Throwable t) {
               view.hideGoogleLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

}
