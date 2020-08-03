package com.example.apothecary.views.view.orderdetails;

import com.example.apothecary.Utils;
import com.example.apothecary.models.userorderdetails.OrderDetail;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailPresenter {
    OrderDetailView view;

    public OrderDetailPresenter(OrderDetailView view) {
        this.view = view;
    }

    public void getOrderDetails(int orderId,int userId){

        view.showLoading();
        Call<List<OrderDetail>> ordersCall = Utils.getApothecaryApi().getUserOrderDetails(orderId, userId);
        ordersCall.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){

                    view.setOrderDetails(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<OrderDetail>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
