package com.example.apothecary.views.view.userorder;

import com.example.apothecary.Utils;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.models.user_order.UserOrder;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderPresenter {

    UserOrderView view;

    public UserOrderPresenter(UserOrderView view) {
        this.view = view;
    }

    public void getUserOrder(int userId){

        view.showLoading();
        Call<UserOrder> ordersCall = Utils.getApothecaryApi().getUserOrderList(userId);
        ordersCall.enqueue(new Callback<UserOrder>() {
            @Override
            public void onResponse(@NonNull Call<UserOrder> call, Response<UserOrder> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){

                    view.setUserOrder(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserOrder> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void getUpdatedUserOrder(String Url){

        if(Url==null || Url.isEmpty() || Url.trim().isEmpty()){
            return;
        }

        view.showUpdatingLoading();
        Call<UserOrder> ordersCall = Utils.getApothecaryApi().getUpdatedUserOrderList(Url);
        ordersCall.enqueue(new Callback<UserOrder>() {
            @Override
            public void onResponse(@NonNull Call<UserOrder> call, Response<UserOrder> response) {
                view.hideUpdatingLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setUpdatedUserOrder(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserOrder> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    void getNewsFeed(){
        view.showLoading();
        Call<List<ListData>> promotionsCall = Utils.getApothecaryApi().getPromotions();
        promotionsCall.enqueue(new Callback<List<ListData>>() {
            @Override
            public void onResponse(@NonNull Call<List<ListData>> call, Response<List<ListData>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setNewsFeed(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ListData>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
