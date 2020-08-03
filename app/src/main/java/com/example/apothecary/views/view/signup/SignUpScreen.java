package com.example.apothecary.views.view.signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.RegisterResponse;
import com.example.apothecary.models.user.RegisterationResponse;

import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.view.login.LoginScreen;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SignUpScreen extends AppCompatActivity implements SignUpView{

    @BindView(R.id.signup_reg_btn)
    CardView signup_btn;
    @BindView(R.id.signup_login_btn)
    Button login_btn;
    @BindView(R.id.signup_password_input)
    TextInputEditText password;
    @BindView(R.id.signup_confirmpassword_input)
    TextInputEditText confirm_Password;
    @BindView(R.id.signup_fullname_input)
    TextInputEditText fullname;
    @BindView(R.id.signup_email_input)
    TextInputEditText email;
    @BindView(R.id.signup_phone_input)
    TextInputEditText contact;
    @BindView(R.id.login_ProgressBar1)
    SpinKitView progressBar;

    SignUpPresenter presenter;
    AwesomeValidation awesomeValidation;

    AlertDialog.Builder descDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        presenter = new SignUpPresenter(this);
        ValidatoinRule();
        LoginButton();
        SignUpButton();
    }

    private void LoginButton(){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void SignUpButton(){
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                    AppUser user = new AppUser(
                            fullname.getText().toString(),
                            contact.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString()
                    );

                    presenter.Register(user);

                }

            }
        });

    }

    private  void ValidatoinRule(){
        awesomeValidation = new AwesomeValidation(BASIC);
        String regexName = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
        awesomeValidation.addValidation(this, R.id.signup_fullname_input,regexName , R.string.invalid_fullname_error);
        awesomeValidation.addValidation(this, R.id.signup_email_input, Patterns.EMAIL_ADDRESS, R.string.invalid_email_error);

        String regexContact = "^[0-9]{11,14}$";
        awesomeValidation.addValidation(this, R.id.signup_phone_input, regexContact, R.string.invalid_phone_error);
        String regexPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 4 characters
                "$";
        awesomeValidation.addValidation(this, R.id.signup_password_input, regexPassword, R.string.invalid_password_error);
        awesomeValidation.addValidation(this, R.id.signup_confirmpassword_input, R.id.signup_password_input, R.string.invalid_confirmpassword_error);
    }

    @Override
    public void showLoading() { progressBar.setVisibility(View.VISIBLE); }

    @Override
    public void hideLoading() { progressBar.setVisibility(View.GONE); }

    @Override
    public void onErrorLoading(String message) { Utils.showDialogMessage(this, "Title", message);
        Utils.showDialogMessage(SignUpScreen.this, "Title", message);
    }

    @Override
    public void setRegisterdUser(RegisterationResponse registerationResponse) {

        if(registerationResponse.getSuccess()==false){

            String ErrorMessage =null;
            String Contact= null, Email=null;

            for(int position=0;position<=registerationResponse.getMessage().getEmail().size();position++){
                Email+=registerationResponse.getMessage().getEmail().get(position);
            }

            for(int position=0;position<=registerationResponse.getMessage().getContact().size();position++){
                Contact+=registerationResponse.getMessage().getContact().get(position);
            }

            if(Email!=null || !Email.trim().isEmpty()){
                ErrorMessage+=Email;
            }

            if(ErrorMessage==null || ErrorMessage.trim().isEmpty()){
                ErrorMessage+=Contact;
            }
            else{
                if(Contact!=null || !Contact.trim().isEmpty()){
                    ErrorMessage= ErrorMessage +" and "+Contact;
                }
            }

              descDialog = new AlertDialog.Builder(SignUpScreen.this)
                      .setTitle(ErrorMessage);

        }
        else
            {
            Toast.makeText(SignUpScreen.this,"Registeration Successfull. Please wait.",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }

        }

    }

