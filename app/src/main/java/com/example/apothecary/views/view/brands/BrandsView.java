package com.example.apothecary.views.view.brands;

import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;

import java.util.List;

public interface BrandsView {
    void showLoading();
    void hideLoading();
    void setOtherBrands(List<Brands> brands);
    void setPopularBrands(List<Brands> brands);
    void onErrorLoading(String message);
}
