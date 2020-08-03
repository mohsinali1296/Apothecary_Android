package com.example.apothecary.views.view.signup;

import com.example.apothecary.Utils;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.helper.APIError;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.RegisterationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter {
    SignUpView view ;

    public SignUpPresenter(SignUpView view) {
        this.view = view;
    }

    void Register(AppUser user){
        view.showLoading();
        Call<RegisterationResponse> userCall = Utils.getApothecaryApi().Register(user);
        userCall.enqueue(new Callback<RegisterationResponse>() {
            @Override
            public void onResponse(Call<RegisterationResponse> call, Response<RegisterationResponse> response) {
                view.hideLoading();

            //    APIError apiError = ApothecaryClient.parseError(response);

                if(response.isSuccessful() && response.body() != null){
                    view.setRegisterdUser(response.body());
                }

            }

            @Override
            public void onFailure(Call<RegisterationResponse> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }
}
