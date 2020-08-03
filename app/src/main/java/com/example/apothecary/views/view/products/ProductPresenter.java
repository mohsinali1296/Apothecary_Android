package com.example.apothecary.views.view.products;

import com.example.apothecary.Utils;
import com.example.apothecary.models.cart.AddCart;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.models.favourite.Favourite;
import com.example.apothecary.models.ratings.AddRatings;
import com.example.apothecary.models.ratings.PharmacyRating;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter {
    ProductView view ;

    public ProductPresenter(ProductView view) {
        this.view = view;
    }

    void getStocksById(int productId){
        view.showLoading();

        Call<List<Stocks>> productCall =  Utils.getApothecaryApi().getStockByProductId(productId);

        productCall.enqueue(new Callback<List<Stocks>>() {
            @Override
            public void onResponse(Call<List<Stocks>> call, Response<List<Stocks>> response) {
                view.hideLoading();
                List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setStock(response.body());
                }else{
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Stocks>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void getStocksBySubCategoryId(int SubCategoryId,int stockId){
        view.showLoading();

        Call<List<Stocks>> productCall =  Utils.getApothecaryApi().getStockBySubCategoryId(SubCategoryId,stockId);

        productCall.enqueue(new Callback<List<Stocks>>() {
            @Override
            public void onResponse(Call<List<Stocks>> call, Response<List<Stocks>> response) {
                view.hideLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setSimilarProducts(response.body());
                }
                else if (response.isSuccessful() && response.body()==null){
                    view.hideSimilarProduct();
                }
                else{
                    view.hideSimilarProduct();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Stocks>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void getStocksByBrandId(int BrandsId,int stockId){
        view.showLoading();

        Call<List<Stocks>> productCall =  Utils.getApothecaryApi().getStockByBrandId(BrandsId,stockId);

        productCall.enqueue(new Callback<List<Stocks>>() {
            @Override
            public void onResponse(Call<List<Stocks>> call, Response<List<Stocks>> response) {
                view.hideLoading();
                List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setSimilarBrandsProducts(response.body());
                }
                else if(response.isSuccessful() && response.body()==null){
                    view.hideSimilarBrandsProduct();
                    view.setSimilarBrandsProducts(null);
                }
                else{
                    view.hideSimilarBrandsProduct();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Stocks>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void checkFavourite(int stockId,int userId){
        //view.showLoading();

        Call<List<Favourite>> productCall =  Utils.getApothecaryApi().checkFavourite(stockId,userId);

        productCall.enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
               // view.hideLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setCheckFavourite(response.body());
                }else if (response.isSuccessful() && response.body()==null){
                    view.setCheckFavourite(null);
                }else{
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {
                //view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void checkWishist(int stockId,int userId){
        //view.showLoading();

        Call<List<Favourite>> productCall =  Utils.getApothecaryApi().checkWishList(stockId,userId);

        productCall.enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                //view.hideLoading();
                List<Favourite> f = response.body();
                if(response.isSuccessful() && response.body()!=null){
                    view.setCheckWishList(response.body());
                }else if (response.isSuccessful() && response.body()==null){
                    view.setCheckWishList(null);
                }else{
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {
                //view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void addFavourits(Favourite fav){
       view.showWishLoading();

        Call<Favourite> productCall =  Utils.getApothecaryApi().AddFavourite(fav);

        productCall.enqueue(new Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {
                view.hideWishLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.setAddFavourite(response.body());
                }

            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable t) {
                view.hideWishLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void deleteFavourite(int stockId, int userId , int type){
      //  view.showUpdatingLoading();
        Call<Void> favouriteCall = Utils.getApothecaryApi().deleteFavouriteByStock(stockId, userId, type);
        favouriteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
        //        view.hideUpdatingLoading();
                if(response.isSuccessful()){
                    view.setDeleteFavourites(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
          //      view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    void CheckCartByStockId(int userId,int stockId){
       // view.showLoading();

        Call<Cart> cartCall =  Utils.getApothecaryApi().checkCartList(userId,stockId);

        cartCall.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
         //       view.hideLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setCheckCartData(response.body());
                }
                else{
                   // view.hideSimilarProduct();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
               // view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void AddtoCart(AddCart cart){
         view.showCartLoading();

        Call<AddCart> cartCall =  Utils.getApothecaryApi().AddtoCart(cart);

        cartCall.enqueue(new Callback<AddCart>() {
            @Override
            public void onResponse(Call<AddCart> call, Response<AddCart> response) {
                view.hideCartLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setAddToCart(response.body());
                }
                else{
                    view.hideCartLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddCart> call, Throwable t) {
                view.hideCartLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void UpdateCart(AddCart cart,int cartId){
        view.showCartLoading();

        Call<UpdateCart> cartCall =  Utils.getApothecaryApi().UpdateCart(cart,cartId);

        cartCall.enqueue(new Callback<UpdateCart>() {
            @Override
            public void onResponse(Call<UpdateCart> call, Response<UpdateCart> response) {
                view.hideCartLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUpdateCart(response.body());
                }
                else{
                    view.hideCartLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call, Throwable t) {
                view.hideCartLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void AddRatings(AddRatings ratings){
        //view.showCartLoading();

        Call<AddRatings> ratingsCall =  Utils.getApothecaryApi().AddRatings(ratings);

        ratingsCall.enqueue(new Callback<AddRatings>() {
            @Override
            public void onResponse(Call<AddRatings> call, Response<AddRatings> response) {
               // view.hideCartLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setAddRatings(response.body());
                }
                else{
                 //   view.hideCartLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddRatings> call, Throwable t) {
                //view.hideCartLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void CheckRatings(int userId){
        //view.showCartLoading();

        Call<List<AddRatings>> ratingsCall =  Utils.getApothecaryApi().checkRatingByUserId(userId);

        ratingsCall.enqueue(new Callback<List<AddRatings>>() {
            @Override
            public void onResponse(Call<List<AddRatings>> call, Response<List<AddRatings>> response) {
                // view.hideCartLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setUserRatings(response.body());
                }
                else{
                    //   view.hideCartLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AddRatings>> call, Throwable t) {
                //view.hideCartLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void PharmacyRatings(int pharmId){
        //view.showCartLoading();

        Call<List<PharmacyRating>> ratingsCall =  Utils.getApothecaryApi().getPharmacyRating(pharmId);

        ratingsCall.enqueue(new Callback<List<PharmacyRating>>() {
            @Override
            public void onResponse(Call<List<PharmacyRating>> call, Response<List<PharmacyRating>> response) {
                // view.hideCartLoading();
                //List<Stocks>  stocksList = response.body();
                //response.errorBody();
                if(response.isSuccessful() && response.body()!=null){
                    view.setPharmacyRatings(response.body());
                }
                else{
                    //   view.hideCartLoading();
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PharmacyRating>> call, Throwable t) {
                //view.hideCartLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
