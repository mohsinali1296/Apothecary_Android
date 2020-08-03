package com.example.apothecary.views.view.cart;

import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;

public interface CartView {
    void showLoading();
    void hideLoading();
    void showUpdatingLoading();
    void hideUpdatingLoading();
    void showRemoveLoading();
    void hideRemoveLoading();

    void setCart(Cart cart);
    void setUpdatedCart(Cart cart);
    void setAddCart(UpdateCart cart);
    void setSubCart(UpdateCart cart);
    void setRemoveCart(int responseCode);
    void setEmptyCart(int responseCode);

    void onErrorLoading(String message);
}
