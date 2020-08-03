package com.example.apothecary.views.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apothecary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Menu_BottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener listener;

    @BindView(R.id.bottomsheet_HomeTextView)
    TextView home_TextView;
    @BindView(R.id.bottomsheet_PharmacyTextView)
    TextView pharmacy_TextView;
    @BindView(R.id.bottomsheet_Brand_TextView)
    TextView brandTextView;
    @BindView(R.id.bottomsheet_Articles_TextView)
    TextView articlesTextView;
    @BindView(R.id.bottomsheet_Profile_TextView)
    TextView profileTextView;
    @BindView(R.id.bottomsheet_Favourite_TextView)
    TextView favouriteTextView;
    @BindView(R.id.bottomsheet_Search_TextView)
    TextView searchTextView;
    @BindView(R.id.bottomsheet_Order_TextView)
    TextView orderTextView;
    @BindView(R.id.bottomsheet_Signout_TextView)
    TextView signoutTextView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_bottomsheet,container,false);
        ButterKnife.bind(this,view);
        MenuItemClickEvent();
        return view;
    }



    void MenuItemClickEvent(){

        home_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_home);
                dismiss();
            }
        });

        pharmacy_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_pharmacy);
                dismiss();
            }
        });

        brandTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_brands);
                dismiss();
            }
        });

        articlesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_blog_articles);
                dismiss();
            }
        });

        profileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_profile);
                dismiss();
            }
        });

        favouriteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_favourite);
                dismiss();
            }
        });

        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_search);
                dismiss();
            }
        });

        orderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBottomClicked(R.layout.fragment_user_order);
                dismiss();
            }
        });
        signoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSignout(true);
                dismiss();
            }
        });
    }

    public interface BottomSheetListener{
        void onBottomClicked(int fragmentId);
        void onSignout(boolean Logout);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            if(context instanceof  Menu_BottomSheet.BottomSheetListener){
                listener = (BottomSheetListener) context;
            }
        }catch (Exception e){
            throw new RuntimeException(context.toString()+" must implement MainMenu_BottomSheet_ClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
