package com.example.apothecary.views.view.checkout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.apothecary.R;
import com.example.apothecary.models.user.AppUser;
import com.example.apothecary.views.view.products.Bottomsheet_AddtoCart;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.example.apothecary.R.id.bottomsheet_LocalAddress_input;
import static com.example.apothecary.R.id.view;

public class BottomSheet_Update_Address extends BottomSheetDialogFragment {

    private BottomSheetListener listener;
    Context context;
    Activity activity;
    AwesomeValidation awesomeValidation;
    int viewId;

    @BindView(bottomsheet_LocalAddress_input)
    TextInputEditText editText_LocalAddress;
    @BindView(R.id.bottomsheet_City_input)
    TextInputEditText editText_City;
    @BindView(R.id.bottomsheet_Country_input)
    TextInputEditText editText_Country;
    @BindView(R.id.bottomsheet_Update_Button)
    Button update_Btn;



    public BottomSheet_Update_Address(Context context , Activity activity) { this.context = context; this.activity=activity; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_changeaddress_layout,container,false);
        ButterKnife.bind(this,view);
        ValidationRule();
        UpdateButtonEvent();
        return view;
    }

    private void UpdateButtonEvent() {
        update_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){

                    String localAddress = editText_LocalAddress.getText().toString();
                    String city = editText_City.getText().toString();
                    String country = editText_Country.getText().toString();
                    listener.updateClicked(localAddress,city,country);
                    dismiss();
                }
            }
        });
    }


    public void setViewId(int viewId){
        this.viewId=viewId;
    }

    public interface BottomSheetListener{
        void updateClicked(String localAddress, String City,  String Country);
    }


    private void ValidationRule(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(editText_LocalAddress, RegexTemplate.NOT_EMPTY, "Please Enter your Local Address");
        awesomeValidation.addValidation(editText_City, RegexTemplate.NOT_EMPTY, "Please Enter your City");
        awesomeValidation.addValidation(editText_Country, RegexTemplate.NOT_EMPTY, "Please Enter your Country");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{

            if(viewId==1){
                listener = (BottomSheetListener) getTargetFragment();
            }else{
                if(context instanceof  BottomSheet_Update_Address.BottomSheetListener){
                    listener = (BottomSheetListener) context;
                }
            }


        }catch (Exception e){
            throw new RuntimeException(context.toString()+" must implement UpdateAddress_BottomSheet_ClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}
