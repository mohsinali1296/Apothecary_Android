package com.example.apothecary.views.view.home;

import com.example.apothecary.Utils;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    void getCategories(){
        view.showLoading();
        Call<List<ListData>> categoriesCall = Utils.getApothecaryApi().getListData(1);
        categoriesCall.enqueue(new Callback<List<ListData>>() {
            @Override
            public void onResponse(@NonNull Call<List<ListData>> call, Response<List<ListData>> response) {
                view.hideLoading();
                //ListData list = (ListData) response.body();
                if(response.isSuccessful() && response.body() != null){
                    view.setCategory(response.body());
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

    void getPopularBrands(){
        view.showLoading();
        Call<List<Brands>> brandsCall = Utils.getApothecaryApi().getLimitedBrands();
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

}
