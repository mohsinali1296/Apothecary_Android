package com.example.apothecary.views.view.checkout;

import com.example.apothecary.models.temporaryuserorder.AddResponseTemporaryOrder;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user_order.AddUserOrder;
import com.example.apothecary.models.user_order.AddUserOrderResponse;
import com.example.apothecary.models.userorderdetails.OrderDetailsResponse;

public interface CheckoutView {

    void showUpdateLoading();
    void hideUpdateLoading();

    void showOrderLoading();
    void hideOrdereLoading();

    void setUpdateUserAddress(AppUser user);
    void setUserOrder(AddUserOrderResponse order);
    void setUserOrderDetails(OrderDetailsResponse order);
    void setEmptyCart(int responseCode);

    void onErrorLoading(String message);
}
