package com.example.apothecary.views.view.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.apothecary.PharmacyMapActivity;
import com.example.apothecary.R;
import com.example.apothecary.adapter.Products_Horizontal_RecyclerView_Adapter;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.cart.AddCart;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.models.favourite.Favourite;
import com.example.apothecary.models.pharmacies.Datum;
import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.ratings.AddRatings;
import com.example.apothecary.models.ratings.PharmacyRating;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ProductsDetailActivity extends AppCompatActivity  implements ProductView
, Bottomsheet_AddtoCart.BottomSheetListener
{

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;// = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
    SharedPreferences preferences;// =  getSharedPreferences(FILE_NAME,MODE_PRIVATE);

    private static final String USER_ID_KEY ="USER_ID";

   // int userId; = preferences.getInt(USER_ID_KEY,0);

    private static Products Products;
    private static Stocks Stocks;
    private static Cart Cart;

    private static int stockID , productID;
    private static int userId;

    private static List<AddRatings> addRatings;

    ProductPresenter presenter;
    static User user;

   Bottomsheet_AddtoCart bottomsheet_addtoCart;

    Products_Horizontal_RecyclerView_Adapter adapter_SimilarProducts , adapter_SimilarBrandProduct;
    GridLayoutManager gridLayoutManager_SimilarBrand_Products , gridLayoutManager_SimilarProducts ;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.produtdetails_ScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.productdetails_ProductImage)
    ImageView productImage;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.subcategory)
    TextView subcategory;
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.pharmacy)
    TextView pharmacy;
    @BindView(R.id.productdetails_DetailedDesc)
    TextView detailedDesc;
    @BindView(R.id.productdetails_ProductDesc)
    TextView desc;
    @BindView(R.id.productdetails_ProductName)
    TextView productName;
    @BindView(R.id.productDetails_Price)
    TextView priceOfProduct;
    @BindView(R.id.productDetails_Piece)
    TextView piece;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.productdetails_Available)
    TextView available;
    @BindView(R.id.LeafPrice)
    TextView LeafPrice;
    @BindView(R.id.BoxPrice)
    TextView BoxPrice;

    @BindView(R.id.Products_Price_LinearLayout)
    LinearLayout price_LinearLayout;

    @BindView(R.id.rating_PharmacyName)
    TextView rating_PharmacyName;
    @BindView(R.id.rating_PharmacyAddress)
    TextView rating_PharmacyAddress;
    @BindView(R.id.rating_PharmacyContact)
    TextView rating_PharmacyContact;
    @BindView(R.id.rating_PharmacyEmail)
    TextView rating_PharmacyEmail;
    @BindView(R.id.rating_PharmacyLocation)
    ImageView rating_location;

    @BindView(R.id.star1)
    ToggleButton star1;
    @BindView(R.id.star2)
    ToggleButton star2;
    @BindView(R.id.star3)
    ToggleButton star3;
    @BindView(R.id.star4)
    ToggleButton star4;
    @BindView(R.id.star5)
    ToggleButton star5;

    @BindView(R.id.wish_ProgressBar)
    SpinKitView wish_ProgressBar;
    @BindView(R.id.check_WishList)
    ImageView check_Image;
    @BindView(R.id.product_favourite_toggle)
    ToggleButton fav_ToggleBtn;
    @BindView(R.id.AddToCartButton)
    TextView addToCart_Text;
    @BindView(R.id.AddToWishlist_Button)
    TextView addToWishList_Text;

    @BindView(R.id.averageRating)
    TextView tv_AvgRating;
    @BindView(R.id.total_ratings_Top)
    TextView tv_TotalRating_Top;
    @BindView(R.id.Rating5_Text)
    TextView tv_Rating5;
    @BindView(R.id.Rating4_Text)
    TextView tv_Rating4;
    @BindView(R.id.Rating3_Text)
    TextView tv_Rating3;
    @BindView(R.id.Rating2_Text)
    TextView tv_Rating2;
    @BindView(R.id.Rating1_Text)
    TextView tv_Rating1;
    @BindView(R.id.total_rating_Bottom)
    TextView tv_TotalRating_Bottom;

    @BindView(R.id.ratingprogressBar5)
    ProgressBar ratingProgressBar5;
    @BindView(R.id.ratingprogressBar4)
    ProgressBar ratingProgressBar4;
    @BindView(R.id.ratingprogressBar3)
    ProgressBar ratingProgressBar3;
    @BindView(R.id.ratingprogressBar2)
    ProgressBar ratingProgressBar2;
    @BindView(R.id.ratingprogressBar1)
    ProgressBar ratingProgressBar1;



    @BindView(R.id.similar_Brands_Products_Recyclerview)
    RecyclerView similarBrandsProducts_Recyclerview;
    @BindView(R.id.similar_Products_Recyclerview)
    RecyclerView similarProducts_Recyclerview;



    ProgressDialog pDialog;

    private static Datum datum ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);

        editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
         preferences =  getSharedPreferences(FILE_NAME,MODE_PRIVATE);

        ButterKnife.bind(this);

         userId = preferences.getInt(USER_ID_KEY,0);

        presenter = new ProductPresenter(this);
        bottomsheet_addtoCart=new Bottomsheet_AddtoCart(this);

        Intent intent = getIntent();
        stockID = intent.getIntExtra("EXTRA_PRODUCT_DETAILS_ID",0);

        presenter.getStocksById(stockID);
        RatingButtonClick();
        FavouriteButton_Events();
        AddtoCartButton_Click();
        LocationButton_Click();
        initDialog();
    }




    private void setupActionBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailedavtivity_searc_menu, menu);
        Intent intent = new Intent(ProductsDetailActivity.this,MainScreen.class);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menu_detail_search:{
                        intent.putExtra("PRODUCT_DETAILS_BACK_ID",2);
                        startActivity(intent);
                    }
                    break;

                    case R.id.menu_detail_cart:{
                        intent.putExtra("PRODUCT_DETAILS_BACK_ID",3);
                        startActivity(intent);
                    }
                        break;

                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerSimilarBrandsProducts).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerSimlarProducts).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.shimmerSimilarBrandsProducts).setVisibility(View.GONE);
        findViewById(R.id.shimmerSimlarProducts).setVisibility(View.GONE);
    }

    @Override
    public void showWishLoading() { wish_ProgressBar.setVisibility(View.VISIBLE); }

    @Override
    public void hideWishLoading() { wish_ProgressBar.setVisibility(View.GONE); }

    @Override
    public void showCartLoading() { this.showpDialog();}

    @Override
    public void hideCartLoading() { this.hidepDialog();}

    @Override
    public void hideSimilarBrandsProduct() {
        findViewById(R.id.similar_brands_products_layout).setVisibility(View.GONE);
    }

    @Override
    public void hideSimilarProduct() {
        findViewById(R.id.similar_products_layout).setVisibility(View.GONE);
    }


    @Override
    public void setStock(List<Stocks> stocks) {

        Stocks = stocks.get(0);

        Picasso.get().load(getImageUrl(stocks.get(0).getImage())).placeholder(R.drawable.ic_circle2).into(productImage);
        category.setText(stocks.get(0).getCategoryName());
        subcategory.setText(stocks.get(0).getSubCategoryName());
        brand.setText(stocks.get(0).getBrandName());
        pharmacy.setText(stocks.get(0).getPharmacyName());
        productName.setText(stocks.get(0).getProductName());
        desc.setText(stocks.get(0).getItemDescription());
        detailedDesc.setText(HtmlCompat.fromHtml(stocks.get(0).getItemDetailedDescription(),0));

        rating_PharmacyName.setText(stocks.get(0).getPharmacyName());
        rating_PharmacyAddress.setText(stocks.get(0).getPharmacyAddress());
        rating_PharmacyContact.setText(stocks.get(0).getPharmacyContact());
        rating_PharmacyEmail.setText(stocks.get(0).getPharmacyEmail());

        datum = new Datum(stocks.get(0).getPharmacyName(),stocks.get(0).getLatitude(),stocks.get(0).getLongitude());

        if(stocks.get(0).getAvailable()==0){
            available.setText("No");
        }else{
            available.setText("Yes");
        }

        if(stocks.get(0).getCategoryId()==48){
            priceOfProduct.setText(stocks.get(0).getUnitPrice().toString());
            piece.setText("(per Tablet)");

            price_LinearLayout.setVisibility(View.VISIBLE);
            LeafPrice.setText("Rs. "+stocks.get(0).getLeafPrice()+"/- per "+stocks.get(0).getQtyPerLeaf()+" Tablets");
            BoxPrice.setText("Rs. "+stocks.get(0).getBoxPrice()+"/- per "+stocks.get(0).getQtyPerBox()+" Tablets");

        }else{
            priceOfProduct.setText(stocks.get(0).getUnitPrice().toString());
            price_LinearLayout.setVisibility(View.VISIBLE);
            if(stocks.get(0).getBoxPrice()!=0){
                LeafPrice.setVisibility(View.GONE);
                BoxPrice.setText("Rs. "+stocks.get(0).getBoxPrice()+"/- per Pack");
            }

        }

        setupActionBar();

        presenter.getStocksByBrandId(stocks.get(0).getBrandId(),stocks.get(0).getProductId());
        presenter.getStocksBySubCategoryId(stocks.get(0).getSubCategoryId(),stocks.get(0).getProductId());
        presenter.checkFavourite(stockID,userId);
        presenter.checkWishist(stockID,userId);
        presenter.CheckCartByStockId(userId,stockID);
        presenter.CheckRatings(userId);
        presenter.PharmacyRatings(stocks.get(0).getPharmacyId());

    }

    @Override
    public void setSimilarProducts(List<Stocks> stocks) {
       if(stocks.size()==0){
           findViewById(R.id.similar_products_layout).setVisibility(View.GONE);
       }
        else {
           findViewById(R.id.similar_products_layout).setVisibility(View.VISIBLE);
           adapter_SimilarProducts = new Products_Horizontal_RecyclerView_Adapter(stocks,this);
           similarProducts_Recyclerview.setAdapter(adapter_SimilarProducts);
           gridLayoutManager_SimilarProducts = new GridLayoutManager(
                   ProductsDetailActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
           similarProducts_Recyclerview.setLayoutManager(gridLayoutManager_SimilarProducts);
           similarProducts_Recyclerview.setNestedScrollingEnabled(true);
           adapter_SimilarProducts.notifyDataSetChanged();

           adapter_SimilarProducts.setOnItemClickListener(new Products_Horizontal_RecyclerView_Adapter.ClickListener() {
               @Override
               public void onClick(View view, int position) {

                   stockID = stocks.get(position).getProductId();
                   presenter.getStocksById(stockID);


                   int toolbarHeight = appBarLayout.getHeight();

                   nestedScrollView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                   nestedScrollView.dispatchNestedPreScroll(0, toolbarHeight, null, null);
                   nestedScrollView.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -toolbarHeight});
                   nestedScrollView.scrollTo(0, 0);

                   appBarLayout.setExpanded(true);


               }
           });
       }

        }

    @Override
    public void setSimilarBrandsProducts(List<Stocks> stocks) {
        if(stocks.size()==0){
            findViewById(R.id.similar_brands_products_layout).setVisibility(View.GONE);
        }
        else  {
            findViewById(R.id.similar_brands_products_layout).setVisibility(View.VISIBLE);
            adapter_SimilarBrandProduct = new Products_Horizontal_RecyclerView_Adapter(stocks, this);
            similarBrandsProducts_Recyclerview.setAdapter(adapter_SimilarBrandProduct);
            gridLayoutManager_SimilarBrand_Products = new GridLayoutManager(
                    ProductsDetailActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
            similarBrandsProducts_Recyclerview.setLayoutManager(gridLayoutManager_SimilarBrand_Products);
            similarBrandsProducts_Recyclerview.setNestedScrollingEnabled(true);
            adapter_SimilarBrandProduct.notifyDataSetChanged();

            adapter_SimilarBrandProduct.setOnItemClickListener(new Products_Horizontal_RecyclerView_Adapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    stockID = stocks.get(position).getProductId();
                    presenter.getStocksById(stockID);


                    int toolbarHeight = appBarLayout.getHeight();

                    nestedScrollView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                    nestedScrollView.dispatchNestedPreScroll(0, toolbarHeight, null, null);
                    nestedScrollView.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -toolbarHeight});
                    nestedScrollView.scrollTo(0, 0);

                    appBarLayout.setExpanded(true);

                }
            });
        }
    }

    @Override
    public void setCheckFavourite(List<Favourite> favourite) {
        List<Favourite> f = favourite;
        if(favourite.size()!=0){
            fav_ToggleBtn.setChecked(true);
        }else{
            fav_ToggleBtn.setChecked(false);
        }
    }

    @Override
    public void setCheckWishList(List<Favourite> favourite) {
        List<Favourite> f = favourite;
        if(favourite.size()!=0){
            check_Image.setVisibility(View.VISIBLE);
        }else{
            check_Image.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAddFavourite(Favourite favourite) {
        if(favourite!=null){

            if(favourite.getType()==0){
                Toast.makeText(this,Stocks.getProductName()+" is added to your Favourite!",Toast.LENGTH_LONG).show();
            }else if (favourite.getType()==1){
                Toast.makeText(this,Stocks.getProductName()+" is added to your  Wishlist!",Toast.LENGTH_LONG).show();
                check_Image.setVisibility(View.VISIBLE);String product = Stocks.getProductName().toString();
            }

        }
    }

    @Override
    public void setDeleteFavourites(int responseCode) {
        if(responseCode==204){
            Toast.makeText(this,"Item Removed from favourites",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setCheckCartData(Cart cart) {
        this.Cart = cart;
    }

    @Override
    public void setAddToCart(AddCart cart) {
        if(cart!=null){
            Toast.makeText(this,"Item added to your Cart Successfully",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUpdateCart(UpdateCart cart) {
        if(cart!=null){
            Toast.makeText(this,"Item added to your Cart Successfully",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setAddRatings(AddRatings ratings) {
        Toast.makeText(this,"Thank you for your reviews.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserRatings(List<AddRatings> ratings) {
        addRatings=ratings;

        if(ratings.size()!=0){

            switch (ratings.get(0).getRating()){
                case 0:
                    rating1Check();
                    break;
                case 2:
                    rating2Check();
                    break;
                case 3:
                    rating3Check();
                    break;
                case 4:
                    rating4Check();
                    break;
                case 5:
                    rating5Check();
                    break;
            }

        }


    }

    @Override
    public void setPharmacyRatings(List<PharmacyRating> pharmacyRatings) {

        if(pharmacyRatings.size()!=0){

            PharmacyRating rating = pharmacyRatings.get(0);
            int sumRating = rating.getRatingsum();
            int rating1 = rating.getRating1();
            int rating2 = rating.getRating2();
            int rating3 = rating.getRating3();
            int rating4 = rating.getRating4();
            int rating5 = rating.getRating5();

            double avg = Math.round(Double.valueOf(rating.getRatingavg().toString())*1000.0)/1000.0;

            tv_AvgRating.setText(Double.toString(avg));
            tv_TotalRating_Top.setText(Integer.toString(sumRating)+" Ratings");
            tv_TotalRating_Bottom.setText(Integer.toString(sumRating));
            tv_Rating1.setText(Integer.toString(rating1));
            tv_Rating2.setText(Integer.toString(rating2));
            tv_Rating3.setText(Integer.toString(rating3));
            tv_Rating4.setText(Integer.toString(rating4));
            tv_Rating5.setText(Integer.toString(rating5));

            int percent_Rating1 = (rating1/sumRating)*100;
            int percent_Rating2 = (rating2/sumRating)*100;
            int percent_Rating3 = (rating3/sumRating)*100;
            int percent_Rating4 = (rating4/sumRating)*100;
            int percent_Rating5 = (rating5/sumRating)*100;

            ratingProgressBar1.setProgress(percent_Rating1);
            ratingProgressBar2.setProgress(percent_Rating2);
            ratingProgressBar3.setProgress(percent_Rating3);
            ratingProgressBar4.setProgress(percent_Rating4);
            ratingProgressBar5.setProgress(percent_Rating5);

        }else{
            UnCheckedAllStars();
        }




    }

    private void AddToFavourites(int type){
        Favourite fav = new Favourite(userId,Stocks.getProductId(),type);
        presenter.addFavourits(fav);
    }

    private void AddtoCartButton_Click(){
        addToCart_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheet_addtoCart.setProduct(Stocks);
                bottomsheet_addtoCart.show(getSupportFragmentManager(),"addToCartBottomSheet");
            }
        });

    }

    private void DeleteFavourites(int type){
        presenter.deleteFavourite(stockID,user.getCustomerId(),type);
    }


    private void AddCart(int qty , int carttype){
        AddCart addCart;
        Double totalPrice = 0.0;

        if(Stocks.getCategoryId()!=48){
            totalPrice = qty*Stocks.getUnitPrice();
        }else{

            switch (carttype){
                case 0:
                    totalPrice = qty*Stocks.getUnitPrice();
                    break;
                case 1:
                    totalPrice = qty*Stocks.getLeafPrice();
                    break;
                case 2:
                    totalPrice = qty*Stocks.getBoxPrice();
                    break;
            }
        }
        addCart = new AddCart(userId,stockID,qty,totalPrice,carttype);
        presenter.AddtoCart(addCart);
    }

    private void UpdateCart(int qty , int carttype){

        AddCart addCart;

        int cartId = Cart.getData().get(0).getCartId();
        Double totalPrice = 0.0;
        if(Stocks.getCategoryId()!=48){
            totalPrice = qty*Stocks.getUnitPrice();
        }else{

            switch (carttype){
                case 0:
                    totalPrice = qty*Stocks.getUnitPrice();
                    break;
                case 1:
                    totalPrice = qty*Stocks.getLeafPrice();
                    break;
                case 2:
                    totalPrice = qty*Stocks.getBoxPrice();
                    break;
            }
        }
        addCart = new AddCart(qty,totalPrice);
        presenter.UpdateCart(addCart,cartId);
    }

    @Override
    public void onAddToCartCicked(int qty , int carttype) {

       if(Cart.getData().size()<=0){
            AddCart(qty,carttype);
        }else{
           if(Cart.getData().get(0).getCartType()==carttype){
               UpdateCart(qty,carttype);
           }else{
                AddCart(qty,carttype);
           }
        }
    }

    @Override
    public void onErrorLoading(String message) {
        Toasty.normal(ProductsDetailActivity.this,message,Toasty.LENGTH_LONG);
    }

    private void FavouriteButton_Events(){

        fav_ToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav_ToggleBtn.isChecked()){
                    AddToFavourites(0);
                    fav_ToggleBtn.setChecked(true);
                }else{
                    DeleteFavourites(0);
                    fav_ToggleBtn.setChecked(false);
                }
            }
        });


        addToWishList_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToFavourites(1);
            }
        });

    }

    private void LocationButton_Click() {
        rating_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductsDetailActivity.this, PharmacyMapActivity.class);
                intent.putExtra("EXTRA_PHARMACYDATA",(Serializable) datum);
                startActivity(intent);
            }
    });
    }

    private void RatingButtonClick(){

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star1.isChecked()){
                    if(addRatings.size()==0){
                        presenter.AddRatings(new AddRatings(Stocks.getPharmacyId(),userId,1));
                        rating1Check();
                    }

                }else{
                    UnCheckedAllStars();
                }
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star2.isChecked()){
                    if(addRatings.size()==0){
                        presenter.AddRatings(new AddRatings(Stocks.getPharmacyId(),userId,2));
                        rating2Check();
                    }
                }else{
                    UnCheckedAllStars();
                }
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star3.isChecked()){
                    if(addRatings.size()==0){
                        presenter.AddRatings(new AddRatings(Stocks.getPharmacyId(),userId,3));
                        rating3Check();
                    }
                }else{
                    UnCheckedAllStars();
                }
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star4.isChecked()){
                    if(addRatings.size()==0){
                        presenter.AddRatings(new AddRatings(Stocks.getPharmacyId(),userId,4));
                        rating4Check();
                    }
                }else{
                    UnCheckedAllStars();
                }
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star5.isChecked()){
                    if(addRatings.size()==0){
                        presenter.AddRatings(new AddRatings(Stocks.getPharmacyId(),userId,5));
                        rating5Check();
                    }
                }else{
                    UnCheckedAllStars();
                }
            }
        });

    }

    private void UnCheckedAllStars(){
        star1.setChecked(false);star2.setChecked(false);star3.setChecked(false);star4.setChecked(false);star5.setChecked(false);
    }

    private void rating1Check(){
        star1.setChecked(true);star2.setChecked(false);star3.setChecked(false);star4.setChecked(false);star5.setChecked(false);
    }

    private void rating2Check(){
        star1.setChecked(true);star2.setChecked(true);star3.setChecked(false);star4.setChecked(false);star5.setChecked(false);
    }

    private void rating3Check(){
        star1.setChecked(true);star2.setChecked(true);star3.setChecked(true);star4.setChecked(false);star5.setChecked(false);
    }

    private void rating4Check(){
        star1.setChecked(true);star2.setChecked(true);star3.setChecked(true);star4.setChecked(true);star5.setChecked(false);
    }

    private void rating5Check(){
        star1.setChecked(true);star2.setChecked(true);star3.setChecked(true);star4.setChecked(true);star5.setChecked(true);
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

}
