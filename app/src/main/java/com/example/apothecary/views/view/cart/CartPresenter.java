package com.example.apothecary.views.view.cart;

import com.example.apothecary.Utils;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.models.pharmacies.Pharmacy;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {
    CartView view;

    public CartPresenter(CartView view) {
        this.view = view;
    }

    public void getCartList(int userId){

        view.showLoading();
        Call<Cart> cartCall = Utils.getApothecaryApi().getCartList(userId);
        cartCall.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, Response<Cart> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setCart(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void getUpdatedCartList(String url){

        view.showUpdatingLoading();
        Call<Cart> cartCall = Utils.getApothecaryApi().getUpdatedCartList(url);
        cartCall.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, Response<Cart> response) {
                view.hideUpdatingLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setUpdatedCart(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void updateAddCart(int cartId){

       // view.showLoading();
        Call<UpdateCart> cartCall = Utils.getApothecaryApi().UpdateAddCart(cartId);
        cartCall.enqueue(new Callback<UpdateCart>() {
            @Override
            public void onResponse(Call<UpdateCart> call, Response<UpdateCart> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.setAddCart(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });


    }

    public void updateSubCart(int cartId){

        // view.showLoading();
        Call<UpdateCart> cartCall = Utils.getApothecaryApi().UpdateSubtractCart(cartId);
        cartCall.enqueue(new Callback<UpdateCart>() {
            @Override
            public void onResponse(Call<UpdateCart> call, Response<UpdateCart> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.setSubCart(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });


    }

    public void removeCart(int cartId){

         view.showRemoveLoading();
        Call<Void> cartCall = Utils.getApothecaryApi().deleteCart(cartId);
        cartCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.hideRemoveLoading();
                if(response.isSuccessful()){
                    view.setRemoveCart(response.code());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.hideRemoveLoading();
                view.onErrorLoading(t.getMessage());

            }
        });


    }

    public void emptyCart(int userId){

        view.showRemoveLoading();
        Call<Void> cartCall = Utils.getApothecaryApi().emptyCart(userId);
        cartCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.hideRemoveLoading();
                if(response.isSuccessful()){
                    view.setEmptyCart(response.code());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.hideRemoveLoading();
                view.onErrorLoading(t.getMessage());

            }
        });


    }

}
