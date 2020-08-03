package com.example.apothecary.views.mainscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter2;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.Body;
import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterationResponse;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.view.login.LoginPresenter;
import com.example.apothecary.views.view.login.LoginScreen;
import com.example.apothecary.views.view.login.LoginView;
import com.example.apothecary.views.view.search.SearchFragment;
import com.example.apothecary.views.view.Category.CategoriesFragment;
import com.example.apothecary.views.view.Category.CategoryMainFragment;
import com.example.apothecary.views.view.articles.BlogArticlesFragment;
import com.example.apothecary.views.view.brands.BrandsFragment;
import com.example.apothecary.views.view.cart.CartFragment;
import com.example.apothecary.views.view.favourites.FavouriteFragment;
import com.example.apothecary.views.view.pharmacy.PharmacyFragment;
import com.example.apothecary.views.view.profile.ProfileFragment;
import com.example.apothecary.views.view.userorder.UserOrderFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.List;

public class MainScreen extends AppCompatActivity implements
        HomeFragment.HomeFragmentListener,
        CategoriesFragment.CategoriesFragmentListener,
        Menu_BottomSheet.BottomSheetListener,
CategoryMainFragment.CategoryMainFragmentLister,
        LoginView
{

    private static final String FILE_NAME ="Apothecary";
    private static final String USER_ID_KEY ="USER_ID";
    private static final String USER_FULLNAME_KEY ="USER_FULLNAME";
    private static final String USER_LOCALADDRESS_KEY ="USER_LOCALADDRESS";
    private static final String USER_CITY_KEY ="USER_CITY";
    private static final String USER_COUNTRY_KEY ="USER_COUNTRY";
    private static final String USER_EMAIL_KEY ="USER_EMAIL";
    private static final String USER_CONTACT_KEY ="USER_CONTACT";
    private static final String USER_LOGGED_KEY ="USER_LOGGED";
    private static final String USER_IMAGE_KEY ="USER_IMAGE";
    //private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;

    LoginPresenter loginPresenter;

    private int userId;
    ProgressDialog pDialog;

    private static String user_Fullname,user_Email;


    SpaceNavigationView navigationView;
    BlogArticlesFragment blogArticlesFragment;
    HomeFragment homeFragment;
    FragmentManager manager;
    CategoryMainFragment categoryMainFragment;
    CategoriesFragment categoriesFragment;
  //  LinearLayout bottomSheetMenu_Layout;
    Menu_BottomSheet menu_BottomSheet;
    BrandsFragment brandsFragment;
    ProfileFragment profileFragment;
    PharmacyFragment pharmacyFragment;
    FavouriteFragment favouriteFragment;
    SearchFragment searchFragment;
    CartFragment cartFragment;
    UserOrderFragment userOrderFragment;

   // CurrentLocation currentLocation;



    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
   // private static final long FAST_UPDATE_IN_MILLI = 5000;
   // private static final long UPDATE_IN_MILLI = 10000;
   // private static final int REUEST_CHECK_SETTINGS = 100;


    private FusedLocationProviderClient fusedLocationClient;
  //  private SettingsClient settingsClient;
  //  private LocationSettingsRequest locationSettingsRequest;
  //  private LocationCallback locationCallback;
  //  private Location location;

    static double latitude , logitude;

    public static double getLatitude() { return latitude; }

    public static double getLongitude() {
        return logitude;
    }


/*
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
*/
    private static User user;

    private static User getUser(){
        return user;
    }

    Dialog internetError_Dialog;


    ImageView close_Button;
    TextView error_Title;
    TextView error_Message;
    Button OkDialog_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        InitializingFragmentAndObjects(savedInstanceState);

        //manager.beginTransaction().add(R.id.fragmentContainer,categoryMainFragment,"Category_Main");

    }


    @Override
    protected void onStart() {
        super.onStart();
        //FetchCurrentLocation();
        FetchUserCurrentLocation();
    }


    void InitializingFragmentAndObjects(Bundle savedInstanceState){
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        //RegisterUser();
        setNavigationView(savedInstanceState);
        editor = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        initDialog();
        blogArticlesFragment= new BlogArticlesFragment();
        categoryMainFragment = new CategoryMainFragment();
        homeFragment = new HomeFragment();
        manager = getSupportFragmentManager();
        menu_BottomSheet=new Menu_BottomSheet();
        categoriesFragment = new CategoriesFragment();
        brandsFragment = new BrandsFragment();
        profileFragment = new ProfileFragment();
        pharmacyFragment = new PharmacyFragment();
        favouriteFragment = new FavouriteFragment();
        searchFragment = new SearchFragment();
        cartFragment = new CartFragment();
        userOrderFragment = new UserOrderFragment();

        getUserInfo();
        getNavigationOfFragment();
        internetError_Dialog = new Dialog(this);



        close_Button=findViewById(R.id.close_NagativeDialog_Image);
        error_Title = findViewById(R.id.title_NegativeDialog_Text);
        error_Message = findViewById(R.id.message_NegativeDialog_Text);
        OkDialog_Button = findViewById(R.id.NegativeDialog_Button);
        internetError_Dialog.setContentView(R.layout.custom_popup_negative);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        FetchUserCurrentLocation();
        CustomDialogClickEvent();
    }

    private void RegisterUser() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainScreen.this);
         if (acct != null) {
             user_Fullname = acct.getDisplayName();
             user_Email = acct.getEmail();
             //Uri personPhoto = acct.getPhotoUrl();
             loginPresenter.CheckUser(user_Email);
         }
    }


    private void FetchUserCurrentLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            logitude = location.getLongitude();


                        }
                    }
                });
    }


    void getUserInfo(){
       // Intent intent = getIntent();
       // user = intent.getParcelableExtra("USER_DATA");

        SharedPreferences preferences =  getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        userId = preferences.getInt(USER_ID_KEY,0);

       // Bundle args = new Bundle();
      //  args.putParcelable("LOGINUSER_DATA_EXTRA",user);
      //  homeFragment.setArguments(args);
       // categoryMainFragment.setArguments(args);
      //  profileFragment.setArguments(args);
        //cart fragment
    }

    void getNavigationOfFragment(){
        Intent intent = getIntent();
        int category = intent.getIntExtra("PRODUCT_DETAILS_BACK_ID",0);
        //String productName = intent.getStringExtra("PRODUCT_DETAILS_BACK_ID");
       // if(category==0){
            HomeTransaction();
       // }
        if(category==1){
            Bundle args = new Bundle();
            args.putInt("TAB_POSITION",0);
            args.putInt("TAB_CATEGORY_ID",1);
            CategoyMainTransaction();

        }
        if(category==2){
            //Bundle args = new Bundle();
            //args.putString();
            //args.putString("Product_Name",productName);
            //searchFragment.setArguments(args);
            SearchTransaction();
        }
        if(category==3){
            CartTransaction();
        }
    }

    private void setNavigationView(Bundle savedInstanceState){
        navigationView=findViewById(R.id.bottom_space);


        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.bottom_space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_menu_250));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_news_250_1));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_search_white_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_shopping_cart_white_24dp));

        navigationView.setCentreButtonSelectable(true);
        navigationView.setCentreButtonSelected();



        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                navigationView.setCentreButtonSelectable(true);
                HomeTransaction();

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

                switch (itemIndex){
                    case 0:{
                        BottomSheetTransaction();
                    }break;
                    case 1:{
                        BlogTransaction();
                    }break;
                    case 2:{
                        SearchTransaction();
                    }break;
                    case 3:{
                        CartTransaction();
                    }break;
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

                switch (itemIndex){
                    case 0:{

                        BottomSheetTransaction();

                    }break;
                    case 1:{
                       BlogTransaction();

                    }break;
                    case 2:{
                        SearchTransaction();

                    }break;
                    case 3:{
                        CartTransaction();
                    }break;
                }

            }
        });
    }

    private void HomeTransaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,homeFragment,"Home_Fragment")
                .commit();
    }

    private void CategoyMainTransaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,categoryMainFragment,"CategoryMain_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void ProfileTransaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,profileFragment,"Profile_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void CartTransaction(){

        manager.beginTransaction()
                .replace(R.id.fragmentContainer,cartFragment,"Cart_Fragment")
                .addToBackStack(null)
                .commit();

    }

    private void SearchTransaction(){

        manager.beginTransaction()
                .replace(R.id.fragmentContainer,searchFragment,"Search_Fragment")
                .addToBackStack(null)
                .commit();

    }

    private void Brand_Trasaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,brandsFragment,"Brand_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void Pharmacy_Transaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,pharmacyFragment,"Pharmacy_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void BlogTransaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,blogArticlesFragment,"Blog_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void Favourite_Transaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,favouriteFragment,"Favourite_Fragment")
                .addToBackStack(null)
                .commit();
    }

   private void  MyOrderTransaction(){
        manager.beginTransaction()
                .replace(R.id.fragmentContainer,userOrderFragment,"UserOrder_Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void BottomSheetTransaction(){
        menu_BottomSheet.show(getSupportFragmentManager(),"menuBottomSheet");
    }


/*
    private void ShowCustom_Error_Dialog(String title, String message){


        error_Title.setText(title);
        error_Message.setText(message);
        CustomDialogClickEvent();
        internetError_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        internetError_Dialog.show();
    }
*/

    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getFragments() != null) {
            for (Fragment frag : fm.getFragments()) {
                if (frag.isVisible()) {
                    FragmentManager chilFrag = frag.getChildFragmentManager();
                    if (chilFrag.getBackStackEntryCount() > 0) {
                        chilFrag.popBackStack();
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
    }

    private void CustomDialogClickEvent(){

/*        close_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetError_Dialog.dismiss();
            }
        });

        OkDialog_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetError_Dialog.dismiss();
            }
        });

 */
    }






    @Override
    public void sendCategoryData(List<ListData> categories, int id ,int position) {

        int catid =id;
        int pos = position;
        CategoryMainFragment categoryMain = new CategoryMainFragment();
        categoryMain.setCategoryData(categories,id,position);

    }

    @Override
    public void sendProductsAdapter(RecyclerViewProductsByCategoryAdapter adapter) {

    }

    @Override
    public void sendStocksAdapter(RecyclerViewProductsByCategoryAdapter2 adapter) {

    }

    @Override
    public void sendStockDetailsToDetailedActivity(int stockId) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void sendProductDetailsToDetailedActivity(int productId) {

    }

    @Override
    public void onBottomClicked(int fragmentId) {
        if(fragmentId==R.layout.fragment_brands){
            Brand_Trasaction();
        }
        if(fragmentId==R.layout.fragment_home){
            HomeTransaction();
        }
        if(fragmentId==R.layout.fragment_blog_articles){
            BlogTransaction();
        }
        if(fragmentId==R.layout.fragment_profile){
            ProfileTransaction();
        }
        if(fragmentId==R.layout.fragment_pharmacy){
            Pharmacy_Transaction();
        }
        if(fragmentId==R.layout.fragment_favourite){
            Favourite_Transaction();
        }
        if(fragmentId==R.layout.fragment_search){
            SearchTransaction();
        }
        if(fragmentId==R.layout.fragment_user_order){
            MyOrderTransaction();
        }
    }

    @Override
    public void onSignout(boolean Logout) {
        if(Logout==true){
            showpDialog();
            editor.putInt(USER_LOGGED_KEY,0);
            editor.apply();
            ExitActivity();
        }
    }

    private void ExitActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // presenter.getStocksData(CategoryId,CURRENT_PAGE,TOTAL_PAGES);
                //presenter.getStocksData(NEXT_PAGE);
                hidepDialog();
                Intent intent = new Intent(MainScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void setTabPosition(int position) {
        CategoriesFragment frag =  new CategoriesFragment() ;
        frag.setTabPosition(position);
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Logging Out...");
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showGoogleLoading() {

    }

    @Override
    public void hideGoogleLoading() {

    }

    @Override
    public void onErrorLoading(String message) {

    }

    @Override
    public void setUserLogin(LoginUserResponse loginUser) {

    }

    @Override
    public void setCheckUser(List<User> user) {
        if(user.size()<=0){
            AppUser appUser  = new AppUser(user_Fullname,user_Email,null);
            loginPresenter.Register(appUser);

        }
    }

    @Override
    public void setRegisterdUser(RegisterationResponse registerationResponse) {
        if(registerationResponse.getSuccess()==false){

            String ErrorMessage =null;
            String  Email=null;

            for(int position=0;position<=registerationResponse.getMessage().getEmail().size();position++){
                Email+=registerationResponse.getMessage().getEmail().get(position);
            }

            if(Email!=null || !Email.trim().isEmpty()){
                ErrorMessage+=Email;
            }


          //  descDialog = new AlertDialog.Builder(MainScreen.this)
            //        .setTitle(ErrorMessage);
            return;
        }
         else if (registerationResponse.getSuccess()==true) {
            Body user = registerationResponse.getBody();

            int userId = user.getId();
            int logged = 1;


            SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
            editor.putInt(USER_ID_KEY, userId);
            editor.putInt(USER_LOGGED_KEY, logged);
            editor.putString(USER_FULLNAME_KEY, user.getFullname().toString());
            editor.putString(USER_LOCALADDRESS_KEY, null);
            editor.putString(USER_CITY_KEY, null);
            editor.putString(USER_COUNTRY_KEY, null);
            editor.putString(USER_CONTACT_KEY, null);
            editor.putString(USER_EMAIL_KEY, user.getEmail());
            editor.putString(USER_IMAGE_KEY, null);

            editor.apply();

             }


    }
}
