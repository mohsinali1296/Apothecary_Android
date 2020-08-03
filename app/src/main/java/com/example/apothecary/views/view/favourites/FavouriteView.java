package com.example.apothecary.views.view.favourites;

import com.example.apothecary.models.favourite.Favourites;
import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;

import java.util.List;

public interface FavouriteView {

    void showLoading();
    void hideLoading();
    void showUpdatingLoading();
    void hideUpdatingLoading();
    void setFavourites(Favourites favourites);
    void setUpdatedFavourites(Favourites favourites);
    void setDeleteFavourites(int responseCode);
    void onErrorLoading(String message);

}
