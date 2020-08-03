package com.example.apothecary.views.view.Category;

import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.stock.Stock;

import java.util.List;

public interface CategoryView {
    void showLoading();
    void hideLoading();
    void showUpdateLoading();
    void hideUpdateLoading();

    void setProducts(Product products);
    void setStocks(Stock stocks);
    void setUpdatedProducts(Product products);
   // void setUpdatedStocks(Stock stocks);
    void onErrorLoading(String message);
}
