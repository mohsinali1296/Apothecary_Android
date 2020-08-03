package com.example.apothecary.views.view.pharmacy;

import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.stock.Stocks;

import java.util.List;

public interface PharmacyView {
    void showLoading();
    void hideLoading();
    void showUpdatingLoading();
    void hideUpdatingLoading();

   void setPharmacies(Pharmacy pharmacy);
    void setUpdatedPharmacies(Pharmacy pharmacy);
    void onErrorLoading(String message);
}
