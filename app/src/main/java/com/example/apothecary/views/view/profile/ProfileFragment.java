package com.example.apothecary.views.view.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.mainscreen.UpdatePassword_BottomSheet;
import com.example.apothecary.views.mainscreen.UploadImage_BottomSheet;
import com.example.apothecary.views.view.checkout.BottomSheet_Update_Address;
import com.example.apothecary.views.view.login.LoginScreen;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProfileFragment extends Fragment implements ProfileView
         ,UploadImage_BottomSheet.UploadImage_BottomSheetListener
         ,BottomSheet_Update_Address.BottomSheetListener
         , UpdatePassword_BottomSheet.BottomSheetListener
{

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;// = getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
    SharedPreferences preferences;// =  getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

    private static final String USER_ID_KEY ="USER_ID";
    private static final String USER_FULLNAME_KEY ="USER_FULLNAME";
    private static final String USER_LOCALADDRESS_KEY ="USER_LOCALADDRESS";
    private static final String USER_CITY_KEY ="USER_CITY";
    private static final String USER_COUNTRY_KEY ="USER_COUNTRY";
    private static final String USER_EMAIL_KEY ="USER_EMAIL";
    private static final String USER_CONTACT_KEY ="USER_CONTACT";
    private static final String USER_IMAGE_KEY ="USER_IMAGE";
    private static final String USER_LOGGED_KEY ="USER_LOGGED";



    View view;
    private static User user;

    private static  String imgURL;

    ProfilePresenter presenter;
    UploadImage_BottomSheet bottomSheet;
    BottomSheet_Update_Address bottomSheet_update_address;
    UpdatePassword_BottomSheet updatePassword_bottomSheet;


    ProgressDialog pDialog;

    private static String POPUP_CONSTANT = "mPopup";
    private static String POPUP_FORCE_SHOW_ICON = "setForceShowIcon";

    @BindView(R.id.profile_UserName)
    TextView profile_UserName;
    @BindView(R.id.profile_Email)
    TextView profile_Email;
    @BindView(R.id.profiledetails_Name)
    TextView fullName;
    @BindView(R.id.profiledetails_Email)
    TextView email;
    @BindView(R.id.profiledetails_Address)
    TextView address;
    @BindView(R.id.profiledetails_Contact)
    TextView contact;
    @BindView(R.id.circleImageView_Profile)
    CircleImageView profileImage;
    @BindView(R.id.edit_ProfileImage)
    CardView editProfileImage_Button;
    @BindView(R.id.profile_SettingButton)
    FloatingActionButton settingButton;

    private static final int PICK_IMAGE=1;
    Uri image_Uri;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
         editor = getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
         preferences =  getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        ButterKnife.bind(this,view);
        presenter = new ProfilePresenter(this);
        InitializeFragementAndObjects();

        return view;
    }

    void InitializeFragementAndObjects(){
        getArgumentsFromMainScreen();
        bottomSheet=new UploadImage_BottomSheet(getContext(),getActivity() , 1);
        bottomSheet_update_address = new BottomSheet_Update_Address(getContext(),getActivity());
        updatePassword_bottomSheet = new UpdatePassword_BottomSheet(getContext(),getActivity());

        setUser(this.user);
        EditProfileImage();
        initDialog();
        SettinButton_Event();

    }


    private void getArgumentsFromMainScreen(){

        int userId = preferences.getInt(USER_ID_KEY,0);
        String fullname = preferences.getString(USER_FULLNAME_KEY,null);
        String localaddress = preferences.getString(USER_LOCALADDRESS_KEY,null);
        String city = preferences.getString(USER_CITY_KEY,null);
        String country = preferences.getString(USER_COUNTRY_KEY,null);
        String email = preferences.getString(USER_EMAIL_KEY,null);
        String contact = preferences.getString(USER_CONTACT_KEY,null);
        String image = preferences.getString(USER_IMAGE_KEY,null);

       this.user = new User(userId,fullname,contact,localaddress,city,country,email,image);


    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }


    private void setUser(User user){

        if(user!=null){
            this.fullName.setText(user.getFullname());
            this.profile_UserName.setText(user.getFullname());
            this.email.setText(user.getEmail());
            this.profile_Email.setText(user.getEmail());
            if(user.getLocalAddress()==null){
                this.address.setText(user.getCountry());
            }else{
                this.address.setText(user.getLocalAddress()+", "+user.getCity()+", "+user.getCountry());
            }
            if(user.getImage()!=null){
                 imgURL = getImageUrl(user.getImage());
                Picasso.get().load(imgURL).into(profileImage);
                bottomSheet.setImageUrl(imgURL);
            }
        }
    }



    private void EditProfileImage(){
        editProfileImage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetTransaction();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenImageDialog();
            }
        });
    }

     void SettinButton_Event(){
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopUp_Menu();
            }
        });
     }


    private void ShowPopUp_Menu() {

        PopupMenu popup = new PopupMenu(getActivity(), view.findViewById(R.id.profile_SettingButton));
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(POPUP_CONSTANT)) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(POPUP_FORCE_SHOW_ICON, boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.user_setting_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.setting_Address:
                        OpenUpdateAddress_BottomSheet();
                        break;

                    case R.id.setting_Password:
                        OpenUpdatePassword_BottomSheet();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void OpenUpdateAddress_BottomSheet(){
        bottomSheet_update_address.setTargetFragment(ProfileFragment.this,2);
        bottomSheet_update_address.setViewId(1);
        bottomSheet_update_address.show(getActivity().getSupportFragmentManager(),"Update_Address_Bottomsheet");
    }

    private void OpenUpdatePassword_BottomSheet(){
        updatePassword_bottomSheet.setTargetFragment(ProfileFragment.this,3);
        updatePassword_bottomSheet.setViewId(1);
        updatePassword_bottomSheet.show(getActivity().getSupportFragmentManager(),"Update_Password_Bottomsheet");
    }

    private void OpenImageDialog(){

        Dialog dialog = new Dialog(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.image_custom_dialogbox,null);
        dialog.setContentView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.Custom_Dialog_Image);
        Button close_btn = (Button) view.findViewById(R.id.Custom_Dialog_Close);
        Picasso.get().load(imgURL).placeholder(R.drawable.ic_circle2).into(imageView);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void BottomSheetTransaction(){
       bottomSheet.setTargetFragment(ProfileFragment.this,1);
        bottomSheet.show(getActivity().getSupportFragmentManager(),"UploadImage_BottomSheet");
    }

    @Override
    public void showProgressLoading() {
        showpDialog();
    }

    @Override
    public void hideProgressLoading() {
        hidepDialog();
    }


    @Override
    public void setUserProfile(AppUser Appuser) {
        if(Appuser.getImage()!=null){

            editor.putString(USER_IMAGE_KEY,Appuser.getImage());
            editor.apply();
            this.user.setImage(Appuser.getImage());
            imgURL = getImageUrl(Appuser.getImage());
            Picasso.get().load(imgURL).into(profileImage);
            bottomSheet.setImageUrl(imgURL);
        }
    }

    @Override
    public void setUpdatedAddress(AppUser user) {
        if(user!=null){
            if(user.getLocalAddress()==null){
                this.address.setText(user.getCountry());
            }else{
                this.address.setText(user.getLocalAddress()+", "+user.getCity()+", "+user.getCountry());
            }
        }
    }

    @Override
    public void setUpdatedPassword(AppUser user) {
        if(user!=null){
            Toast.makeText(getContext(),"Password has been changed Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Title", message);
    }


    @Override
    public void onUploadButtonClicked(String photo) {
        presenter.Upload_ProfileImage(this.user.getCustomerId(),photo);
    }

    @Override
    public void onPrescriptionUploadButtonClicked(String photo, String mediapath) {

    }


    protected void initDialog() {

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
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

    @Override
    public void updateBtnClicked(String password) {

        if(!password.trim().isEmpty() && password!=null){
            presenter.Update_Password(this.user.getCustomerId(),password);
        }

    }
}
