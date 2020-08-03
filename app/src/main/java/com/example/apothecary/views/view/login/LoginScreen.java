package com.example.apothecary.views.view.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.Body;
import com.example.apothecary.models.user.LoginUserResponse;
import com.example.apothecary.models.user.RegisterationResponse;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.helper.CurrentLocation;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.example.apothecary.views.view.signup.SignUpScreen;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class LoginScreen extends AppCompatActivity implements
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
    CurrentLocation currentLocation;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    LoginPresenter presenter;

    private static String user_Fullname , user_Email , user_Password;

    private static final int RC_SIGN_IN=0,RC_MAINSCREEN=1;


    @BindView(R.id.login_signup_btn)
    Button signup_btn;
    @BindView(R.id.login_login_btn)
    CardView login_btn;
    @BindView(R.id.login_google_btn)
    CardView google_btn;
    @BindView(R.id.login_email_input)
    TextInputEditText email;
    @BindView(R.id.login_password_input)
    TextInputEditText password;
    @BindView(R.id.login_ProgressBar)
    SpinKitView progressBar;
    @BindView(R.id.login_bottom_ProgressBar)
    SpinKitView bottomProgressBar;

    AwesomeValidation awesomeValidation;

    AlertDialog.Builder descDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
       ButterKnife.bind(this);
       presenter = new LoginPresenter(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginScreen.this, gso);

        ValidationRule();
        ButtonEvents();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }

    private void ButtonEvents() {
        LoginButton();
        SignUpButton();
        GoogleButton();
    }


    private void GoogleButton() {
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    private void LoginButton(){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                    String user_email = email.getText().toString();
                    String user_pass = password.getText().toString();
                    AppUser user  = new AppUser(user_email,user_pass);
                    presenter.UserLogin(user);
                }

            }
        });
    }

    private void SignUpButton(){
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if(requestCode==RC_MAINSCREEN && resultCode==RESULT_OK){
            startActivity(data);
            finish();
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginScreen.this);
            Intent intent  = new Intent(LoginScreen.this,MainScreen.class);
            startActivity(intent);
            finish();
           // if (acct != null) {
           //     user_Fullname = acct.getDisplayName();
           //     user_Email = acct.getEmail();
           //     Uri personPhoto = acct.getPhotoUrl();
           //     presenter.CheckUser(user_Email);
           // }

        } catch (ApiException e) {
                Toast.makeText(LoginScreen.this, GoogleSignInStatusCodes.getStatusCodeString(e.getStatusCode())
                        ,Toast.LENGTH_LONG).show();
        }
    }


    private void ValidationRule(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(this, R.id.login_email_input, Patterns.EMAIL_ADDRESS, R.string.invalid_email_error);
        awesomeValidation.addValidation(this, R.id.login_password_input, ".{8,}", R.string.invalid_password_error);
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showGoogleLoading() {
        bottomProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGoogleLoading() {
        bottomProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }

    @Override
    public void setUserLogin(LoginUserResponse loginUser) {

        if(loginUser==null){
            Toasty.error(LoginScreen.this,"Incorrect Email or Password").show();
        }else if(loginUser!=null){
            if(loginUser.getError()==false){
                //Toasty.success(LoginScreen.this,loginUser.getBody().get(0).getFullname()).show();
                User user = loginUser.getBody().get(0);

                int userId = loginUser.getBody().get(0).getCustomerId();
                int logged = 1;



                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
                editor.putInt(USER_ID_KEY,userId);
                editor.putInt(USER_LOGGED_KEY,logged);
                editor.putString(USER_FULLNAME_KEY,loginUser.getBody().get(0).getFullname().toString());
                editor.putString(USER_LOCALADDRESS_KEY,loginUser.getBody().get(0).getLocalAddress().toString());
                editor.putString(USER_CITY_KEY,loginUser.getBody().get(0).getCity().toString());
                editor.putString(USER_COUNTRY_KEY,loginUser.getBody().get(0).getCountry().toString());
                editor.putString(USER_CONTACT_KEY,loginUser.getBody().get(0).getContact().toString());
                editor.putString(USER_EMAIL_KEY,loginUser.getBody().get(0).getEmail().toString());
                editor.putString(USER_IMAGE_KEY,loginUser.getBody().get(0).getImage().toString());

                editor.apply();


                Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                Intent intent2 = new Intent(LoginScreen.this, ProductsDetailActivity.class);
                //intent.putExtra("USER_DATA",user);
                //intent2.putExtra("USER_DATA_PRODUCTDETAILS",user);
                startActivity(intent);
                finish();
            }else{
                Toasty.error(LoginScreen.this,"Incorrect Email or Password").show();
            }
        }


    }


             @Override
    public void setCheckUser(List<User> user) {

        if(user.size()<=0){
            AppUser appUser  = new AppUser(user_Fullname,user_Email,null);
            presenter.Register(appUser);

        }

    }

    @Override
    public void setRegisterdUser(RegisterationResponse registerationResponse) {

        Intent intent = new Intent(LoginScreen.this, MainScreen.class);
        startActivityForResult(intent,RC_MAINSCREEN);

        if(registerationResponse.getSuccess()==false){

            String ErrorMessage =null;
            String  Email=null;

            for(int position=0;position<=registerationResponse.getMessage().getEmail().size();position++){
                Email+=registerationResponse.getMessage().getEmail().get(position);
            }

            if(Email!=null || !Email.trim().isEmpty()){
                ErrorMessage+=Email;
            }


            descDialog = new AlertDialog.Builder(LoginScreen.this)
                    .setTitle(ErrorMessage);
            return;
        }
       // else if (registerationResponse.getSuccess()==true)
       // {
            Body user =  registerationResponse.getBody();

            int userId = user.getId();
            int logged = 1;


            SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
            editor.putInt(USER_ID_KEY,userId);
            editor.putInt(USER_LOGGED_KEY,logged);
            editor.putString(USER_FULLNAME_KEY,user.getFullname().toString());
            editor.putString(USER_LOCALADDRESS_KEY,null);
            editor.putString(USER_CITY_KEY,null);
            editor.putString(USER_COUNTRY_KEY,null);
            editor.putString(USER_CONTACT_KEY,null);
            editor.putString(USER_EMAIL_KEY,user.getEmail());
            editor.putString(USER_IMAGE_KEY,null);

            editor.apply();

       // }

        Intent mainScreenIntent = new Intent(LoginScreen.this, MainScreen.class);
        //startActivityForResult(mainScreenIntent,RC_MAINSCREEN);
        setResult(RESULT_OK,mainScreenIntent);

        //Intent intent = new Intent(LoginScreen.this, MainScreen.class);
        //startActivity(intent);
        //finish();
    }


}
