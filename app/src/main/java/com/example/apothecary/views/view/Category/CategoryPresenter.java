package com.example.apothecary.views.view.Category;

import android.view.View;

import com.example.apothecary.Utils;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.stock.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {

    private CategoryView view;

    public CategoryPresenter(CategoryView view) {
        this.view = view;
    }

    void getProductsByCategory(int category,double latitude , double longitude,int distance){
        view.showLoading();
           Call<Product> productCall =  Utils.getApothecaryApi().getAllProducts(category,latitude,longitude,distance);
           productCall.enqueue(new Callback<Product>() {
               @Override
               public void onResponse(Call<Product> call, Response<Product> response) {
                   view.hideLoading();
                   //view.find
                   if(response.isSuccessful() && response.body()!=null){
                        view.setProducts(response.body());
                   }else{
                       view.onErrorLoading(response.message());
                   }
               }

               @Override
               public void onFailure(Call<Product> call, Throwable t) {
                   view.hideLoading();
                   view.onErrorLoading(t.getLocalizedMessage());
               }
           });
        }

    void getStocksByCategory(int category){
        view.showLoading();

            Call<Stock> productCall =  Utils.getApothecaryApi().getAllStocks(category);

            productCall.enqueue(new Callback<Stock>() {
                @Override
                public void onResponse(Call<Stock> call, Response<Stock> response) {
                    view.hideLoading();
                    if(response.isSuccessful() && response.body()!=null){
                        view.setStocks(response.body());
                        //view.setStocks(response.body());
                    }else{
                        view.onErrorLoading(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Stock> call, Throwable t) {
                    view.hideLoading();
                    view.onErrorLoading(t.getLocalizedMessage());
                }
            });
        }

    void getUpdatedProductsByCategory(String url){
        view.showUpdateLoading();
        Call<Product> productCall =  Utils.getApothecaryApi().getUpdatedProductList(url);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                view.hideUpdateLoading();
                //view.find
                if(response.isSuccessful() && response.body()!=null){
                    view.setUpdatedProducts(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideUpdateLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    }


