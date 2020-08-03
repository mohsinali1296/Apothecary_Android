package com.example.apothecary.views.view.products;

import com.example.apothecary.models.cart.AddCart;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.models.favourite.Favourite;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.ratings.AddRatings;
import com.example.apothecary.models.ratings.PharmacyRating;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;

import java.util.List;

public interface ProductView {

    void showLoading();
    void hideLoading();
    void showWishLoading();
    void hideWishLoading();
    void showCartLoading();
    void hideCartLoading();

    void hideSimilarBrandsProduct();
    void hideSimilarProduct();
    void setStock(List<Stocks> stocks);
    void setSimilarProducts(List<Stocks> stocks);
    void setSimilarBrandsProducts(List<Stocks> stocks);
    void setCheckFavourite(List<Favourite> favourite);
    void setCheckWishList(List<Favourite> favourite);
    void setAddFavourite(Favourite favourite);
    void setDeleteFavourites(int responseCode);
    void setCheckCartData(Cart cart);
    void setAddToCart(AddCart cart);
    void setUpdateCart(UpdateCart cart);
    void setAddRatings(AddRatings ratings);
    void setUserRatings(List<AddRatings> ratings);
    void setPharmacyRatings(List<PharmacyRating> ratings);
    void onErrorLoading(String message);


}
