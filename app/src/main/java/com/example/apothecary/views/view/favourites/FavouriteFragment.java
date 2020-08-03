package com.example.apothecary.views.view.favourites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.Favourite_RecyclerView_Adaptar;
import com.example.apothecary.models.favourite.Datum;
import com.example.apothecary.models.favourite.Favourites;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView {

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private static final String USER_ID_KEY ="USER_ID";

    private static int userId;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.favourite_AppBarLayout)
    AppBarLayout appBar;
    @BindView(R.id.favourite_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.favourite_CardView)
    CardView logoCardView;
    @BindView(R.id.favourite_Recyclerview)
    RecyclerView favouriteRecyclerView;
    @BindView(R.id.favourite_ScrollProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.favourite_NestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.favourite_AnimationBackground)
    RelativeLayout animation_BG;
    @BindView(R.id.emptyList_Animation)
    LottieAnimationView animationView;
    @BindView(R.id.favourite_SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    View view;
    FavouritePresenter presenter;
    LinearLayoutManager linearLayoutManager;
    Favourite_RecyclerView_Adaptar adaptar;
    HomeFragment homeFragment;

    List<Favourites> favouritesList;
    List<Datum> datum_ArrayList;

    private Datum removeItem=null;

    Favourites FAVOURITES;

    private static String NEXT_PAGE_URL;
    static int CURRENT_PAGE;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;

    static int tabPostion=0;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this,view);
         editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
         preferences =  getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

         this.userId = preferences.getInt(USER_ID_KEY,0);

        presenter = new FavouritePresenter(this);
        setupTabLayout();
        setTab_Listener();
        setToolbar();
        SwipeRefreshLayoutListener();
        setHasOptionsMenu(true);
        favouritesList = new ArrayList<>();
        datum_ArrayList = new ArrayList<>();
        homeFragment = new HomeFragment();
        presenter.getWishList(this.userId,0);
        return view;
    }



    private void setTab_Listener(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              if(tab.getPosition()==0){
                  tabPostion =tab.getPosition();
                  datum_ArrayList.clear();
                    presenter.getWishList(userId,0);
              }
              if(tab.getPosition()==1){
                  tabPostion =tab.getPosition();
                  datum_ArrayList.clear();
                  presenter.getWishList(userId,1);
              }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(),tabLayout.getSelectedTabPosition(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Favourites"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Wishlist"));
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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,homeFragment,"Home_Fragment")
                        .commit();
            }
        });

    }

    @Override
    public void showLoading() { view.findViewById(R.id.shimmerfavourite).setVisibility(View.VISIBLE); }
    @Override
    public void hideLoading() { view.findViewById(R.id.shimmerfavourite).setVisibility(View.GONE);}

    @Override
    public void showUpdatingLoading() { progressbar.setVisibility(View.VISIBLE); }

    @Override
    public void hideUpdatingLoading() { progressbar.setVisibility(View.GONE);}

    @Override
    public void setFavourites(Favourites favourites) { setStockData(favourites); }

    @Override
    public void setUpdatedFavourites(Favourites favourites) { setStockData(favourites); }

    @Override
    public void setDeleteFavourites(int responseCode) {
        if(responseCode==204){
            Toast.makeText(getActivity(),"Item Removed Successfully",Toast.LENGTH_LONG).show();
            adaptar.notifyDataSetChanged();

        }else{
            Toast.makeText(getActivity(),"Item Not Deleted",Toast.LENGTH_LONG).show();
            datum_ArrayList.add(removeItem);
            adaptar.notifyDataSetChanged();
        }


    }

    private void setStockData(Favourites favourites){

        if(favourites.getData().size()<=0){
            swipeRefreshLayout.setRefreshing(false);
            nestedScrollView.setVisibility(View.GONE);
            favouriteRecyclerView.setVisibility(View.GONE);
            animation_BG.setVisibility(View.VISIBLE);
        }else{
            //animationView.loop(true);
            swipeRefreshLayout.setRefreshing(false);
            animation_BG.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            favouriteRecyclerView.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.VISIBLE);


            FAVOURITES = favourites;

            if(favourites.getData()!=null){
                NEXT_PAGE_URL = favourites.getNextPageUrl();
                CURRENT_PAGE=favourites.getCurrentPage();

                datum_ArrayList.addAll(favourites.getData());

                adaptar= new Favourite_RecyclerView_Adaptar(datum_ArrayList,getActivity());
                favouriteRecyclerView.setAdapter(adaptar);
                linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                favouriteRecyclerView.setLayoutManager(linearLayoutManager);
                favouriteRecyclerView.setNestedScrollingEnabled(false);
                favouriteRecyclerView.setClipToPadding(false);
                adaptar.notifyDataSetChanged();


                adaptar.setOnItemClickListener(new Favourite_RecyclerView_Adaptar.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        int product_Id = datum_ArrayList.get(position).getProductId();

                        Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                        intent.putExtra("EXTRA_PRODUCT_DETAILS_ID",product_Id);
                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(int position) {

                        presenter.deleteFavourite(datum_ArrayList.get(position).getFavId());
                        removeItem = datum_ArrayList.get(position);
                        datum_ArrayList.remove(position);
                        adaptar.notifyDataSetChanged();
                        if(datum_ArrayList.size()<=0){
                            nestedScrollView.setVisibility(View.GONE);
                            favouriteRecyclerView.setVisibility(View.GONE);
                            animation_BG.setVisibility(View.VISIBLE);
                        }else{
                            nestedScrollView.setVisibility(View.VISIBLE);
                            favouriteRecyclerView.setVisibility(View.VISIBLE);
                            animation_BG.setVisibility(View.GONE);
                        }
                    }

                });


                Recyclerview_OnScroll();
                NestedScrollView_OnScroll();

            }
        }

    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Title", message);
    }

    private void Recyclerview_OnScroll(){
        favouriteRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    presenter.getUpdatedWishList(FAVOURITES.getNextPageUrl());
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
                if(FAVOURITES.getNextPageUrl()!=null){
                presenter.getUpdatedWishList(FAVOURITES.getNextPageUrl());
                }else{
                    progressbar.setVisibility(View.GONE);
                }

            }
        }, 2500);
    }


    private void SwipeRefreshLayoutListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datum_ArrayList.clear();
                adaptar.notifyDataSetChanged();
                if(tabPostion==0){
                    presenter.getWishList(userId,0);
                }else if(tabPostion==1){
                    presenter.getWishList(userId,1);
                }
            }
        });

    }
}
