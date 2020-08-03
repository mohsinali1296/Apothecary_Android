package com.example.apothecary.views.view.brands;

import com.example.apothecary.Utils;
import com.example.apothecary.models.brands.Brands;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandsPresenter {

    BrandsView view ;

    public BrandsPresenter(BrandsView view) {
        this.view = view;
    }

    void getPopularBrands(){
        view.showLoading();
        Call<List<Brands>> brandsCall = Utils.getApothecaryApi().getAllBrands();
        brandsCall.enqueue(new Callback<List<Brands>>() {
            @Override
            public void onResponse(@NonNull Call<List<Brands>> call, Response<List<Brands>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){

                    view.setPopularBrands(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Brands>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    void getOtherBrands(){
        view.showLoading();
        Call<List<Brands>> brandsCall = Utils.getApothecaryApi().getOtherBrands();
        brandsCall.enqueue(new Callback<List<Brands>>() {
            @Override
            public void onResponse(@NonNull Call<List<Brands>> call, Response<List<Brands>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){

                    view.setOtherBrands(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Brands>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
