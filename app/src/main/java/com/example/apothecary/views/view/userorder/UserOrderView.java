package com.example.apothecary.views.view.userorder;

import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.user_order.UserOrder;

import java.util.List;

public interface UserOrderView {
    void showLoading();
    void hideLoading();
    void showUpdatingLoading();
    void hideUpdatingLoading();

    void setUserOrder(UserOrder order);
    void setUpdatedUserOrder(UserOrder order);
    void setNewsFeed(List<ListData> newsFeed);
    void onErrorLoading(String message);
}
