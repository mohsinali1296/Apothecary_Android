package com.example.apothecary.views.view.search;


import com.example.apothecary.Utils;
import com.example.apothecary.models.product.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    Search_View view;


    public SearchPresenter(Search_View view  ) {
        this.view = view;
    }

    public void SearchProduct(String name , double latitude , double longitude, double distance){
    view.showUpdatingLoading();
        Call<Product> productCall =  Utils.getApothecaryApi().getSearchedProduct(name,latitude,longitude,distance);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                view.hideLoading();
                //view.find
                if(response.isSuccessful() && response.body()!=null){
                    view.hideUpdatingLoading();
                    view.showLoading();
                    view.setSearchProduct(response.body());
                    view.hideLoading();
                }else{
                    view.hideUpdatingLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void SearchUpdatedProduct(String url){
        view.showUpdatingLoading();
        Call<Product> productCall =  Utils.getApothecaryApi().getUpdatedProductList(url);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                view.hideLoading();
                //view.find
                if(response.isSuccessful() && response.body()!=null){
                    view.hideUpdatingLoading();
                    view.setUpdatedProduct(response.body());
                }else{
                    view.hideUpdatingLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
