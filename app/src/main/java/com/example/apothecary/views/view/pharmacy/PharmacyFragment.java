package com.example.apothecary.views.view.pharmacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.apothecary.PharmacyMapActivity;
import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.Pharmacy_Adapter;
import com.example.apothecary.models.pharmacies.Datum;
import com.example.apothecary.models.pharmacies.Pharmacy;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PharmacyFragment extends Fragment
        implements PharmacyView

{

    final  static double LATITUDE = 31.528808, LONGITUDE = 74.368788;

    @BindView(R.id.pharmacy_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.pharmacy_Recyclerview)
    RecyclerView pharmacyRecyclerView;
    @BindView(R.id.pharmacy_ScrollProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.pharmacy_NestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.pharmacy_CardView)
    CardView logoCardView;

    View view;
    PharmacyPresenter presenter;
    LinearLayoutManager linearLayoutManager;
    HomeFragment homeFragment;

    List<Pharmacy> pharmacyList;
    List<Datum> pharmacy_ArrayList;

    Pharmacy_Adapter adaptar;
    Pharmacy PHARMACY;

    private static String NEXT_PAGE_URL;
    static int CURRENT_PAGE;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;


    Menu menu;

    public PharmacyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pharmacy, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        pharmacyList = new ArrayList<>();
        pharmacy_ArrayList = new ArrayList<>();
        presenter = new PharmacyPresenter(this);
        homeFragment = new HomeFragment();
        setToolbar();
        //presenter.getPharmacies(LATITUDE,LONGITUDE);
        presenter.getPharmacies(MainScreen.getLatitude(),MainScreen.getLongitude());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showLoading() { view.findViewById(R.id.shimmerPhamacy).setVisibility(View.VISIBLE); }

    @Override
    public void hideLoading() {
        view.findViewById(R.id.shimmerPhamacy).setVisibility(View.GONE);
    }

    @Override
    public void showUpdatingLoading() { progressbar.setVisibility(View.VISIBLE); }

    @Override
    public void hideUpdatingLoading() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void setPharmacies(Pharmacy pharmacy) {
        SetPharmacyData(pharmacy);
    }

    @Override
    public void setUpdatedPharmacies(Pharmacy pharmacy) {
        SetPharmacyData(pharmacy);
    }

    private void SetPharmacyData(Pharmacy pharmacy){

        PHARMACY = pharmacy;

        if(pharmacy.getData()!=null){
            NEXT_PAGE_URL = pharmacy.getNextPageUrl();
            CURRENT_PAGE=pharmacy.getCurrentPage();

            pharmacy_ArrayList.addAll(pharmacy.getData());

            adaptar= new Pharmacy_Adapter(getActivity(),pharmacy_ArrayList);
            pharmacyRecyclerView.setAdapter(adaptar);
            linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            pharmacyRecyclerView.setLayoutManager(linearLayoutManager);
            pharmacyRecyclerView.setNestedScrollingEnabled(false);
            pharmacyRecyclerView.setClipToPadding(false);
            adaptar.notifyDataSetChanged();

            adaptar.setOnItemClickListener(new Pharmacy_Adapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Datum datum = pharmacy_ArrayList.get(position);
                    Intent intent = new Intent(getActivity(), PharmacyMapActivity.class);
                    intent.putExtra("EXTRA_PHARMACYDATA",(Serializable) datum);
                    startActivity(intent);

                }
            });

            Recyclerview_OnScroll();
            NestedScrollView_OnScroll();

        }

    }

    private void Recyclerview_OnScroll(){
        pharmacyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrolledOutItems=linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems)){
                    isScrolling=false;
                    //presenter.getUpdatedPharmacies(PHARMACY.getNextPageUrl());
                }
            }
        });
    }

    private void NestedScrollView_OnScroll(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        progressbar.setVisibility(View.VISIBLE);
                        currentItems = linearLayoutManager.getChildCount();
                        totalItems = linearLayoutManager.getItemCount();
                        scrolledOutItems = linearLayoutManager.findFirstVisibleItemPosition();


                        if((currentItems+scrolledOutItems==totalItems)){
                            isScrolling=false;
                            fetchData();

                        }

                    }
                }
            }
        });
    }

    private void fetchData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PHARMACY.getNextPageUrl()!=null){
                    presenter.getUpdatedPharmacies(PHARMACY.getNextPageUrl());
                }else{
                    progressbar.setVisibility(View.GONE);
                }

            }
        }, 2500);
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Title", message);
    }

    private void setToolbar(){
        toolbar.setNavigationIcon(null);

        logoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,homeFragment,"Home_Fragment")
                        .commit();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        toolbar.inflateMenu(R.menu.category_main_menu);
        menu = toolbar.getMenu();

        final MenuItem item = menu.findItem(R.id.action_Searchbar);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptar.getFilter().filter(newText);
                return false;

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_main_menu,this.menu);
       final MenuItem item = menu.findItem(R.id.action_Searchbar);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptar.getFilter().filter(newText);
                return false;

            }
        });

    }

}
