package com.example.apothecary.views.view.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.ConfirmCart_Recyclerview_Adapter;
import com.example.apothecary.models.cart.Datum;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.User;
import com.example.apothecary.models.user_order.AddUserOrder;
import com.example.apothecary.models.user_order.AddUserOrderResponse;
import com.example.apothecary.models.userorderdetails.AddUserOrderDetails;
import com.example.apothecary.models.userorderdetails.OrderDetailsResponse;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.mainscreen.UploadImage_BottomSheet;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements CheckoutView
        ,BottomSheet_Update_Address.BottomSheetListener
        ,UploadImage_BottomSheet.UploadImage_BottomSheetListener
{

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private static final String USER_ID_KEY ="USER_ID";
    private static final String USER_FULLNAME_KEY ="USER_FULLNAME";
    private static final String USER_LOCALADDRESS_KEY ="USER_LOCALADDRESS";
    private static final String USER_CITY_KEY ="USER_CITY";
    private static final String USER_COUNTRY_KEY ="USER_COUNTRY";
    private static final String USER_EMAIL_KEY ="USER_EMAIL";
    private static final String USER_CONTACT_KEY ="USER_CONTACT";
    private static final String USER_IMAGE_KEY ="USER_IMAGE";


    @BindView(R.id.checkout_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.checkout_CardView)
    CardView logoCardView;
    @BindView(R.id.checkout_OrderRecyclerview)
    RecyclerView cartRecyclerview;
    @BindView(R.id.checkout_UserName)
    TextView tv_UserName;
    @BindView(R.id.checkout_LocalAddress)
    TextView tv_LocalAddress;
    @BindView(R.id.checkout_City)
    TextView tv_City;
    @BindView(R.id.checkout_Contact)
    TextView tv_Contact;
    @BindView(R.id.checkout_Country)
    TextView tv_Country;
    @BindView(R.id.checkout_Email)
    TextView tv_Email;
    @BindView(R.id.checkout_OrderSummary)
    TextView tv_Summary;
    @BindView(R.id.checkout_TotalAmount)
    TextView tv_TotalAmount;
    @BindView(R.id.checkout_ImageText)
    TextView tv_Image;
    @BindView(R.id.checkout_UploadImage)
    TextView btn_UploadImage;
    @BindView(R.id.checkout_Order_Btn)
    TextView btn_Order;
    @BindView(R.id.checkout_ChangeAddress_Btn)
    TextView btn_ChangeAddress;
    @BindView(R.id.checkout_Cash_RadioButton)
    MaterialRadioButton rb_Cash;
    @BindView(R.id.checkout_Card_RadioButton)
    MaterialRadioButton rb_Card;
    @BindView(R.id.checkout_ProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.checkout_OrderProgressBar)
    SpinKitView orderProgressbar;

    LinearLayoutManager linearLayoutManager;
    BottomSheet_Update_Address bottomSheet_update_address;
    UploadImage_BottomSheet uploadImage_bottomSheet;

    ProgressDialog pDialog , orderDialog;
    AlertDialog.Builder alertDialogBuilder;

    static String image_Photo=null, Mediapath=null;
    private static int count;

    Dialog confirmOrder_Dialog;
    double Total_Amount;



   private static List<Datum> cart_ArrayList;
    private static User user;

    ConfirmCart_Recyclerview_Adapter adaptar;

    CheckoutPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
         editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
         preferences =  getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        ButterKnife.bind(this);
        InitializeComponents();

    }

    private void InitializeComponents(){
        presenter = new CheckoutPresenter(this);
        cart_ArrayList = new ArrayList<>();
        user = new User();
        bottomSheet_update_address=new BottomSheet_Update_Address(this,this);
        uploadImage_bottomSheet = new UploadImage_BottomSheet(this,this,2);
        confirmOrder_Dialog = new Dialog(this);
        GetIntent();
        setUserInfo();
        setCartDetails();
        setToolbar();
        UpdateAddress_ButtonEvent();
        initDialog();
        initOrderDialog();
        UploadPrescription();
        PlaceUserOrder();
        this.rb_Cash.setChecked(true);
    }




    void GetIntent(){
        Intent intent = getIntent();
        cart_ArrayList = intent.getParcelableArrayListExtra("CONFIRM_CART_LIST");

        int userId = preferences.getInt(USER_ID_KEY,0);
        String fullname = preferences.getString(USER_FULLNAME_KEY,null);
        String localaddress = preferences.getString(USER_LOCALADDRESS_KEY,null);
        String city = preferences.getString(USER_CITY_KEY,null);
        String country = preferences.getString(USER_COUNTRY_KEY,null);
        String email = preferences.getString(USER_EMAIL_KEY,null);
        String contact = preferences.getString(USER_CONTACT_KEY,null);
        String image = preferences.getString(USER_IMAGE_KEY,null);
        String imageUrl=null;


        this.user = new User(userId,fullname,contact,localaddress,city,country,email,imageUrl);


    }

    void setUserInfo(){

        tv_UserName.setText(user.getFullname());
        tv_LocalAddress.setText(user.getLocalAddress());
        tv_City.setText(user.getCity());
        tv_Country.setText(user.getCountry());
        tv_Email.setText(user.getEmail());
        tv_Contact.setText(user.getContact());


    }

    private void setCartDetails(){

        progressbar.setVisibility(View.VISIBLE);

        tv_Summary.setText("You Order Summary ("+cart_ArrayList.size()+")");

        Total_Amount=0.0;
        for(int i=0;i<cart_ArrayList.size();i++){
            Total_Amount+=cart_ArrayList.get(i).getTotalPrice();
        }
        tv_TotalAmount.setText("Rs. "+Total_Amount+"/-");

        adaptar= new ConfirmCart_Recyclerview_Adapter(this,cart_ArrayList);
        cartRecyclerview.setAdapter(adaptar);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cartRecyclerview.setLayoutManager(linearLayoutManager);
        cartRecyclerview.setNestedScrollingEnabled(false);
        cartRecyclerview.setClipToPadding(false);
        adaptar.notifyDataSetChanged();

        progressbar.setVisibility(View.GONE);
    }

    private void setToolbar(){
        toolbar.setNavigationIcon(null);

        logoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void UpdateAddress_ButtonEvent() {
        btn_ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { bottomSheet_update_address.show(getSupportFragmentManager(),"Update_Address_Bottomsheet"); }
        });

    }

    private void UploadPrescription() {
        btn_UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mediapath!=null){
                    uploadImage_bottomSheet.setMediaPath(Mediapath);
                }
                uploadImage_bottomSheet.show(getSupportFragmentManager(),"Update_Address_Bottomsheet");
            }
        });
    }

    @Override
    public void updateClicked(String localAddress, String City, String Country) {

        if((localAddress.trim().isEmpty() || localAddress==null)
        &&(City.trim().isEmpty() || City==null)
        &&(Country.trim().isEmpty() || City==null)){

        }else{
            user.setLocalAddress(localAddress);
            user.setCity(City);
            user.setCountry(Country);

            editor.putString(USER_LOCALADDRESS_KEY,localAddress);
            editor.putString(USER_CITY_KEY,City);
            editor.putString(USER_COUNTRY_KEY,Country);
            editor.apply();

            User updateduser = new User(localAddress,City,Country);

            presenter.Update_Address(this.user.getCustomerId(),updateduser);
        }



    }

    private void PlaceUserOrder() {
        btn_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRequired=isPresciptionRequired();
               if( isRequired && image_Photo==null){
                   Toast.makeText(CheckOutActivity.this,"Please Upload Presciption Image",Toast.LENGTH_LONG).show();
               }
               else if(!isRequired) {
                   showOrderDialog();
                   int userId = user.getCustomerId();
                   double total_amount = Total_Amount;
                   AddUserOrder order = new AddUserOrder(userId,cart_ArrayList.size(),total_amount,null);
                   presenter.AddUserOrder(order);
               }
            }
        });
    }


    @Override
    public void showUpdateLoading() {
        showpDialog();
    }

    @Override
    public void hideUpdateLoading() {
        hidepDialog();
    }

    @Override
    public void showOrderLoading() { orderProgressbar.setVisibility(View.GONE);}

    @Override
    public void hideOrdereLoading() { orderProgressbar.setVisibility(View.GONE);}

    @Override
    public void setUpdateUserAddress(AppUser user) {
        if(user!=null){
            setUserInfo();
        }
    }

    @Override
    public void setUserOrder(AddUserOrderResponse order) {
        if(order!=null){
            int orderId = order.getId();
            for (int position=0; position<cart_ArrayList.size();position++){

                showOrderDialog();

                int userId = user.getCustomerId();
                int stockId = cart_ArrayList.get(position).getProductId();
                int qty = cart_ArrayList.get(position).getQuantity();
                int pharmId = cart_ArrayList.get(position).getPharmacyId();
                int type = cart_ArrayList.get(position).getCartType();
                double price=0;
                double totalPrice=0;

                switch (type){
                    case 0:
                        price= cart_ArrayList.get(position).getUnitPrice();
                        totalPrice = price*qty;
                        break;
                        case 1:
                            price= cart_ArrayList.get(position).getLeafPrice();
                            totalPrice = price*qty;
                            break;
                            case 2:
                                price= cart_ArrayList.get(position).getBoxPrice();
                                totalPrice = price*qty;
                                break;
                        }
                        AddUserOrderDetails orderDetails =new AddUserOrderDetails(userId,orderId,pharmId,stockId,qty,totalPrice,price,type);
                        presenter.AddUserOrderDetails(orderDetails);
                    }

            hideOrderDialog();
        }
    }

    @Override
    public void setUserOrderDetails(OrderDetailsResponse order) {
        if(order!=null){
            count++;
            if(count==cart_ArrayList.size()){
                hideOrderDialog();
                presenter.emptyCart(user.getCustomerId());
                cart_ArrayList.clear();
            }
        }
    }

    @Override
    public void setEmptyCart(int responseCode) {
            if(responseCode==204){
                OpenConfirmOrderDialog();
            }
    }

    @Override
    public void onErrorLoading(String message) {

        Utils.showDialogMessage(this, "Title", message);
    }

    private  void showAlertDialog(String message){
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                ;
        AlertDialog alert = alertDialogBuilder.create();
        alert.setTitle("Error");
        alert.show();
    }

    protected void initDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_updating));
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }


    protected void initOrderDialog() {

        orderDialog = new ProgressDialog(this);
        orderDialog.setMessage(getString(R.string.msg_wait));
        orderDialog.setCancelable(true);
    }

    protected void showOrderDialog() {

        if (!orderDialog.isShowing()) orderDialog.show();
    }

    protected void hideOrderDialog() {

        if (orderDialog.isShowing()) orderDialog.dismiss();
    }

    @Override
    public void onUploadButtonClicked(String photo) {

    }

    @Override
    public void onPrescriptionUploadButtonClicked(String photo, String mediapath) {


        if(photo!=null){
            image_Photo=photo;
            Mediapath=mediapath;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
            tv_Image.setText("img_"+timeStamp+".jpg");
        }
    }



     private void OpenConfirmOrderDialog(){

        Dialog successdialog = new Dialog(CheckOutActivity.this);
         successdialog.setContentView(R.layout.ordersuccesfull_customdialogbox);
        Button home_btn = successdialog.findViewById(R.id.homeBtn);


         home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToHomeScreen();
                successdialog.dismiss();
            }
        });

         successdialog.show();
            }


    private void MoveToHomeScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                confirmOrder_Dialog.dismiss();
                Intent intent = new Intent(CheckOutActivity.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }

    private boolean isPresciptionRequired(){
        for(int position=0;position<cart_ArrayList.size();position++){
            if(cart_ArrayList.get(position).getPrescription_required()==1){
               return true;
            }
        }
        return false;
    }
}
