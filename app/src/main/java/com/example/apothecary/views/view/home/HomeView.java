package com.example.apothecary.views.view.home;

import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;

import java.util.List;

public interface HomeView {

    void showLoading();
    void hideLoading();
    void setNewsFeed(List<ListData> newsFeed);
    void setCategory(List<ListData> category);
    void setPopularBrands(List<Brands> brands);
    void onErrorLoading(String message);
}
