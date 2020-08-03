package com.example.apothecary.views.view.search;

import com.example.apothecary.models.product.Product;

public interface Search_View {
    void showLoading();
    void hideLoading();
    void showUpdatingLoading();
    void hideUpdatingLoading();
    void setSearchProduct(Product product);
    void setUpdatedProduct(Product product);
    void onErrorLoading(String messag);

}
