package com.example.apothecary.views.view.orderdetails;

import com.example.apothecary.models.userorderdetails.OrderDetail;

import java.util.List;

public interface OrderDetailView {
    void showLoading();
    void hideLoading();
    void setOrderDetails(List<OrderDetail> orderDetails);
    void onErrorLoading(String message);
}
