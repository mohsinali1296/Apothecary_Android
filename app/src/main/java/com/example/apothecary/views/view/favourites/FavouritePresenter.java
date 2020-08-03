package com.example.apothecary.views.view.favourites;

import com.example.apothecary.Utils;
import com.example.apothecary.models.favourite.Favourites;
import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.views.view.pharmacy.PharmacyView;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritePresenter {
    FavouriteView view;

    public FavouritePresenter(FavouriteView view) {
        this.view = view;
    }

    public void getWishList(int userId , int type){

        view.showLoading();
        Call<Favourites> favouriteCall = Utils.getApothecaryApi().getAllWishList(userId,type);
        favouriteCall.enqueue(new Callback<Favourites>() {
            @Override
            public void onResponse(@NonNull Call<Favourites> call, Response<Favourites> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setFavourites(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Favourites> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void getUpdatedWishList(String url){

        view.showUpdatingLoading();
        Call<Favourites> favouriteCall = Utils.getApothecaryApi().getUpdatedWishList(url);
        favouriteCall.enqueue(new Callback<Favourites>() {
            @Override
            public void onResponse(@NonNull Call<Favourites> call, Response<Favourites> response) {
                view.hideUpdatingLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setUpdatedFavourites(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Favourites> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void deleteFavourite(int favId){
        view.showUpdatingLoading();
        Call<Void> favouriteCall = Utils.getApothecaryApi().deleteFavourite(favId);
        favouriteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.hideUpdatingLoading();
                if(response.isSuccessful()){
                        view.setDeleteFavourites(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
