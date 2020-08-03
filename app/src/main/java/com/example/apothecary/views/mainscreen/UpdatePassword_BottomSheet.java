package com.example.apothecary.views.mainscreen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.apothecary.R;
import com.example.apothecary.views.view.checkout.BottomSheet_Update_Address;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.example.apothecary.R.id.bottomsheet_LocalAddress_input;
import static com.example.apothecary.R.id.bottomsheet_password_input;
import static com.example.apothecary.R.id.view;

public class UpdatePassword_BottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener listener;
    Context context;
    Activity activity;
    AwesomeValidation awesomeValidation;

    @BindView(bottomsheet_password_input)
    TextInputEditText editText_Password;
    @BindView(R.id.bottomsheet_confirmpassword_input)
    TextInputEditText editText_ConfirmPassword;
    @BindView(R.id.bottomsheet_Update_Button)
    Button update_Btn;

    int viewId;

    public UpdatePassword_BottomSheet(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_changepassword_layout,container,false);
        ButterKnife.bind(this,view);
        ValidationRule();
        UpdateButtonEvent();
        editText_Password.setText(null);
        editText_ConfirmPassword.setText(null);
        editText_Password.setHint("Password");
        editText_ConfirmPassword.setHint("Confirm Password");
        return view;
    }

    private void UpdateButtonEvent() {
        update_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){

                    String password = editText_Password.getText().toString();

                    listener.updateBtnClicked(password);
                    dismiss();
                }
            }
        });
    }

    public void setViewId(int viewId){
        this.viewId=viewId;
    }

    public interface BottomSheetListener{
        void updateBtnClicked(String password);
    }


    private void ValidationRule(){
        awesomeValidation = new AwesomeValidation(BASIC);

        String regexPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 4 characters
                "$";
        awesomeValidation.addValidation(editText_Password, regexPassword,
                "Minimum 8 character are required. At least 1 Uppercase,1 Lowercase and 1 numeric character. No Special Characters and Spaces");
        awesomeValidation.addValidation(editText_ConfirmPassword, editText_Password,
                "Password does not match");

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            if(viewId==1){
                listener = (BottomSheetListener) getTargetFragment();
            }else{
                if(context instanceof  UpdatePassword_BottomSheet.BottomSheetListener){
                    listener = (BottomSheetListener) context;
                }
            }

        }catch (Exception e){
            throw new RuntimeException(context.toString()+" must implement UpdatePassword_BottomSheet_ClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }


}
