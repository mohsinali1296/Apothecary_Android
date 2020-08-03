package com.example.apothecary.api;

import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.cart.AddCart;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.models.favourite.Favourite;
import com.example.apothecary.models.favourite.Favourites;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.pharmacy.Pharmacies;
import com.example.apothecary.models.pharmacy.Pharmacy;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.ratings.AddRatings;
import com.example.apothecary.models.ratings.PharmacyRating;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;
import com.example.apothecary.models.temporaryuserorder.AddResponseTemporaryOrder;
import com.example.apothecary.models.temporaryuserorder.AddTemporaryOrder;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterResponse;
import com.example.apothecary.models.user.RegisterationResponse;
import com.example.apothecary.models.user.User;
import com.example.apothecary.models.user_order.AddUserOrder;
import com.example.apothecary.models.user_order.AddUserOrderResponse;
import com.example.apothecary.models.user_order.UserOrder;
import com.example.apothecary.models.userorderdetails.AddUserOrderDetails;
import com.example.apothecary.models.userorderdetails.OrderDetail;
import com.example.apothecary.models.userorderdetails.OrderDetailsResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApothecaryApi {

    @GET("api/getlistdata/{listid}/{listname}")
    Call <List<ListData>> getListData(@Path("listid") int listid,@Path("listname") int listname);

    @GET("api/getlistdata/{listid}")
    Call <List<ListData>> getListData(@Path("listid") int listid);

    @GET("api/getpromotions")
    Call <List<ListData>> getPromotions();

    @GET("api/getbrands/{id}")
    Call <List<Brands>> getBrandsBySubCategoryId(@Path("id") int subCategoryId );

    @GET("api/getbrands")
    Call <List<Brands>> getAllBrands();

    @GET("api/getotherbrands")
    Call <List<Brands>> getOtherBrands();

    @GET("api/getlimitedbrands")
    Call <List<Brands>> getLimitedBrands();

    @GET("api/getlimitedbrands/{id}")
    Call <List<Brands>> getLimitedBrandsBySubCategoryId(@Path("id") int subCategoryId);

    @GET("api/getallstocks/{id}")
    Call <Stock> getStocks(@Path("id") int id , @Query("page") int page);

    @GET("api/getallstocks/{id}")
    Call <Stock> getAllStocks(@Path("id") int categoryId);
	
	 @GET("api/getsearchedstock/{name}/{lat}/{long}/{distance}")
    Call <Product> getSearchedProduct(@Path("name") String product,
                                    @Path("lat") double latitude , @Path("long") double longitude,
                                    @Path("distance") double distance);
    @GET
    Call<Product> getUpdatedProductList(@Url String url);

    @GET("api/getproductbyid/{id}")
    Call <Stocks> getStockById(@Path("id") int productId);

    @GET("api/getproductbyid/{id}")
    Call <List<Stocks>> getStockByProductId(@Path("id") int productId);

    @GET("api/getproductbysubcategory/{id}/{stockid}")
    Call <List<Stocks>> getStockBySubCategoryId(@Path("id") int subcategoryId,@Path("stockid") int stockId);

    @GET("api/getproductbybrand/{id}/{stockid}")
    Call <List<Stocks>> getStockByBrandId(@Path("id") int brandId,@Path("stockid") int stockId);

    @GET("api/getallstocks/{id}/{lat}/{long}/{distance}")
    Call<Product> getAllProducts(@Path("id") int categoryId ,
                                 @Path("lat") double latitude , @Path("long") double longitude,
                                 @Path("distance") int distance);

    @GET("api/getpharmacylist")
    Call<List<Pharmacy>> getAllPharmacies();

    @GET("api/getpharmacies/{lat}/{long}")
    Call<com.example.apothecary.models.pharmacies.Pharmacy> getPharmacies( @Path("lat") double latitude , @Path("long") double longitude);
    @GET
    Call<com.example.apothecary.models.pharmacies.Pharmacy> getPharmacy(@Url String url);

    @GET("api/getnearpharm/{lat}/{long}")
    Call<List<Pharmacies>> getAllNearPharmacies(@Path("lat") double latitude , @Path("longi") double longitude );

    @GET("api/checkwishlist/{stockId}/{userId}")
    Call<List<Favourite>> checkWishList(@Path("stockId") int stockId,@Path("userId") int userId);

    @GET("api/checkfav/{stockId}/{userId}")
    Call<List<Favourite>> checkFavourite(@Path("stockId") int stockId,@Path("userId") int userId);

    @GET("api/getuserwishlist/{userId}/{type}")
    Call<Favourites> getAllWishList(@Path("userId") int userId , @Path("type") int type);

    @GET
    Call<Favourites> getUpdatedWishList(@Url String url);



    @DELETE("api/deletefavourite/{id}")
    Call<Void> deleteFavourite(@Path("id") int favId);

    @DELETE("api/deletefavourite/{stockId}/{userId}/{type}")
    Call<Void> deleteFavouriteByStock(@Path("stockId") int stock, @Path("userId") int user , @Path("type") int type);

    @GET
    Call<Stock> getStocks(@Url String url);

    @GET
    Call<Product> getProducts(@Url String url);

    @GET("api/getuser/{Id}")
    Call<List<User>> getUserById(@Path("Id") int userId );

    @GET("api/checkuser/{email}")
    Call<List<User>> checkUser(@Path("email") String email );

    @GET("api/login/{email}/{pass}")
    Call<List<User>> Login(@Path("email") String email , @Path("pass") String pass  );

    @POST("api/addfavourite")
    Call<Favourite> AddFavourite(@Body Favourite fav);

    @POST("api/login")
    Call<LoginUserResponse> Login(@Body AppUser user);

    @POST("api/registeruser")
    Call<RegisterationResponse> Register(@Body AppUser user);

    @POST("api/googleregister")
    Call<RegisterationResponse> RegisterWithGoogle(@Body AppUser user);

    @POST("api/addtocart")
    Call<AddCart> AddtoCart(@Body AddCart cart);

    @PATCH("api/addtocart/{id}")
    Call<UpdateCart> UpdateCart(@Body AddCart cart , @Path("id") int cartId);

    @PATCH("api/addtocartadd/{id}")
    Call<UpdateCart> UpdateAddCart(@Path("id") int cartId);

    @PATCH("api/addtocartsub/{id}")
    Call<UpdateCart> UpdateSubtractCart(@Path("id") int cartId);

    @DELETE("api/addtocart/{id}")
    Call<Void> deleteCart(@Path("id") int cartId);

    @DELETE("api/deletecart/{userId}")
    Call<Void> emptyCart(@Path("userId") int userId);

    @GET("api/addtocart/{userId}")
    Call<Cart> getCartList(@Path("userId") int userId);

    @GET
    Call<Cart> getUpdatedCartList(@Url String url);

    @GET("api/addtocart/{userId}/{stockId}")
    Call<Cart> checkCartList(@Path("userId") int userId , @Path("stockId") int stockId );

    @FormUrlEncoded
    @POST("api/registeruser")
    Call<List<User>> Register(@Field("fullname") String fullname,
                        @Field("contact") String contact,
                        @Field("email") String email,
                       @Field("pass") String pass);

     @Multipart
    @PATCH("api/addprofileimage/{Id}")
    Call<AppUser> uploadProfileImage(@Path("Id") int userId, @Part MultipartBody.Part photo);

    @Multipart
    @PATCH("api/addprofileimage/{Id}")
    Call<RequestBody> upload_Profile_Image(@Path("Id") int Id , @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @PATCH("api/addprofileimage/{Id}")
    Call<AppUser> upload_Profile_Image(@Path("Id") int Id , @Field("image") String photo);

    @POST("api/ratings")
    Call<AddRatings> AddRatings(@Body AddRatings ratings);

    @GET("api/checkratings/{userId}")
    Call<List<AddRatings>> checkRatingByUserId(@Path("userId") int userId);


    @PATCH("api/updateaddress/{Id}")
    Call<AppUser> UpdateAddress(@Path("Id") int Id, @Body User user);

    @FormUrlEncoded
    @PATCH("api/updatepassword/{Id}")
    Call<AppUser> UpdatePassword(@Path("Id") int Id, @Field("pass") String pass);

    @POST("api/userorder")
    Call<AddUserOrderResponse> AddUserOrder(@Body AddUserOrder order);

    @GET("api/userorder/{userId}")
    Call<UserOrder> getUserOrderList(@Path("userId") int userId);

    @GET("api/userorderdetails/{orderId}/{userId}")
    Call<List<OrderDetail>> getUserOrderDetails(@Path("orderId") int orderId , @Path("userId") int userId);

    @GET
    Call<UserOrder> getUpdatedUserOrderList(@Url String url);

    @POST("api/userorderdetails")
    Call<OrderDetailsResponse> AddUserOrderDetails(@Body AddUserOrderDetails order);


    @GET("api/ratings/{pharmId}")
    Call<List<PharmacyRating>> getPharmacyRating(@Path("pharmId") int pharmId);


   /* @Multipart
    @PATCH("api/addprofileimage/{Id}")
    Call<List<User>> uploadProfileImage(@Path("Id") int userId, @PartMap Map<String , RequestBody> photo);
    */

    /* @Multipart
    @PATCH("api/uploadimage")
    Call<List<AppUser>> uploadProfileImage(@Part("description") RquestBody desciption, @Part MultipartBody.Part photo);*/

}
