package com.example.apothecary.views.view.checkout;

import com.example.apothecary.Utils;
import com.example.apothecary.models.temporaryuserorder.AddResponseTemporaryOrder;
import com.example.apothecary.models.temporaryuserorder.AddTemporaryOrder;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.User;
import com.example.apothecary.models.user_order.AddUserOrder;
import com.example.apothecary.models.user_order.AddUserOrderResponse;
import com.example.apothecary.models.userorderdetails.AddUserOrderDetails;
import com.example.apothecary.models.userorderdetails.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPresenter {

    CheckoutView view;

    public CheckoutPresenter(CheckoutView view) {
        this.view = view;
    }


    public void Update_Address(int userId,  User user){

        view.showUpdateLoading();

        Call<AppUser> checkoutCall = Utils.getApothecaryApi().UpdateAddress(userId,user);

        checkoutCall.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                view.hideUpdateLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUpdateUserAddress(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                view.hideUpdateLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

    public void AddUserOrder(AddUserOrder order){

        view.showOrderLoading();

        Call<AddUserOrderResponse> checkoutCall = Utils.getApothecaryApi().AddUserOrder(order);

        checkoutCall.enqueue(new Callback<AddUserOrderResponse>() {
            @Override
            public void onResponse(Call<AddUserOrderResponse> call, Response<AddUserOrderResponse> response) {
                view.hideOrdereLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUserOrder(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddUserOrderResponse> call, Throwable t) {
                view.hideOrdereLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

    public void AddUserOrderDetails(AddUserOrderDetails order){

        Call<OrderDetailsResponse> checkoutCall = Utils.getApothecaryApi().AddUserOrderDetails(order);

        checkoutCall.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    view.setUserOrderDetails(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                view.hideOrdereLoading();
                view.onErrorLoading(t.getMessage());
            }
        });

    }

    public void emptyCart(int userId){

        Call<Void> cartCall = Utils.getApothecaryApi().emptyCart(userId);
        cartCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    view.setEmptyCart(response.code());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onErrorLoading(t.getMessage());

            }
        });


    }

}
