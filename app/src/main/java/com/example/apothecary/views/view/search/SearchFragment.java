package com.example.apothecary.views.view.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SearchFragment extends Fragment implements Search_View {

    View view;
    SearchPresenter presenter;
    Menu menu;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.search_AppBarLayout)
    AppBarLayout appBar;
    @BindView(R.id.search_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_CardView)
    CardView logoCardView;
    @BindView(R.id.search_Recyclerview)
    RecyclerView searchRecyclerView;
    @BindView(R.id.search_ScrollProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.search_NestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.search_AnimationBackground)
    RelativeLayout animation_BG;
    @BindView(R.id.search_AnimationBackground2)
    RelativeLayout animation_BG2;
    @BindView(R.id.noItem_Animation)
    LottieAnimationView animationView;
    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton area_FloatingBtn ;

    List<Products> productsArrayList;
    Product PRODUCT;
    LinearLayoutManager manager;

    HomeFragment homeFragment;

    private static RecyclerViewProductsByCategoryAdapter adapter;


    private static String NEXT_PAGE_URL;
    static int CURRENT_PAGE;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;

    static  String query_Product=null;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        homeFragment = new HomeFragment();
        presenter = new SearchPresenter(this);
        InitializingComponents();
        return view;
    }

    private void InitializingComponents() {
        productsArrayList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        setToolbar();
        setupTabLayout();
        getBundlesArgs();
    }

    private void getBundlesArgs() {
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            String productName = (String) bundle.get("Product_Name");
            presenter.SearchProduct(productName, MainScreen.getLatitude(),MainScreen.getLongitude() , 10.0);
        }

    }

    @Override
    public void showLoading() { view.findViewById(R.id.shimmerSearch).setVisibility(View.VISIBLE); }

    @Override
    public void hideLoading() { view.findViewById(R.id.shimmerSearch).setVisibility(View.GONE); }

    @Override
    public void showUpdatingLoading() { progressbar.setVisibility(View.VISIBLE); }

    @Override
    public void hideUpdatingLoading() { progressbar.setVisibility(View.GONE); }

    @Override
    public void setSearchProduct(Product products) { setProductsData(products); }

    @Override
    public void setUpdatedProduct(Product products) { setProductsData(products); }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(),"Title",message);
    }

    private void setProductsData(Product products){
        if(products.getData().size()<=0){
            animation_BG2.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.GONE);
            animation_BG.setVisibility(View.VISIBLE);
        }
        else
        {
            animation_BG2.setVisibility(View.GONE);
            animation_BG.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.VISIBLE);
            PRODUCT = products;

            productsArrayList.addAll((Collection<? extends Products>) products.getData());

            adapter = new RecyclerViewProductsByCategoryAdapter(getActivity(),productsArrayList);
            searchRecyclerView.setLayoutManager(manager);
            searchRecyclerView.setNestedScrollingEnabled(false);
            searchRecyclerView.setClipToPadding(false);
            searchRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            adapter.setOnItemClickListener(new RecyclerViewProductsByCategoryAdapter.ClickListener() {

                @Override
                public void onClick( View view, int position) {
                    int productId = products.getData().get(position).getProductId();
                    Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                    intent.putExtra("EXTRA_PRODUCT_DETAILS_ID",productId);
                    startActivity(intent);

                }

            });
            Recyclerview_OnScroll();
            NestedScrollView_OnScroll();

        }
    }


    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Products"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Alternatives"));
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

        final MenuItem searchItem = menu.findItem(R.id.action_Searchbar);
         final androidx.appcompat.widget.SearchView searchView_Component = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView_Component.setQueryHint("Search...");

        searchView_Component.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.trim().isEmpty()){
                    animation_BG2.setVisibility(View.VISIBLE);
                    animation_BG.setVisibility(View.GONE);
                    if(productsArrayList.size()!=0){
                        productsArrayList.clear();
				     adapter.notifyDataSetChanged();
                        
                    }

                }else{
                    if(productsArrayList.size()!=0){
                        productsArrayList.clear();
                        adapter.notifyDataSetChanged();

                    }
                    query_Product = query;
                    presenter.SearchProduct(query_Product,MainScreen.getLatitude(),MainScreen.getLongitude() ,10.0);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.trim().isEmpty()){
                    animation_BG2.setVisibility(View.VISIBLE);
                    animation_BG.setVisibility(View.GONE);
                    if(productsArrayList.size()!=0){
                        productsArrayList.clear();
                        adapter.notifyDataSetChanged();
                    }

                }
                return true;
            }
        });

    }


    private void Recyclerview_OnScroll(){
        searchRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems=manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems)){
                    isScrolling=false;
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
                        currentItems = manager.getChildCount();
                        totalItems = manager.getItemCount();
                        scrolledOutItems = manager.findFirstVisibleItemPosition();


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
                if(PRODUCT.getNextPageUrl()!=null){
                presenter.SearchUpdatedProduct(PRODUCT.getNextPageUrl());
                }else{
                    progressbar.setVisibility(View.GONE);
                }

            }
        }, 2500);
    }

    private void setFloatingActionButton() {
        final ImageView floating_icon = new ImageView(getActivity());
        floating_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_area_radar));
        area_FloatingBtn=
                new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                        .setContentView(floating_icon).build();
        FloatingActionButton.LayoutParams layoutParams = (FloatingActionButton.LayoutParams) area_FloatingBtn.getLayoutParams();
        layoutParams.setMargins(0,0,16,125);
        area_FloatingBtn.setLayoutParams(layoutParams);

        SubActionButton.Builder subBuilder =  new SubActionButton.Builder(getActivity());

        ImageView deleteIcon=new ImageView(getActivity());
        deleteIcon.setImageResource(R.drawable.ic_five);
        SubActionButton five_Btn=subBuilder.setContentView(deleteIcon).build();

        ImageView removeIcon=new ImageView(getActivity());
        removeIcon.setImageResource(R.drawable.ic_ten);
        SubActionButton ten_Btn=subBuilder.setContentView(removeIcon).build();

        ImageView addIcon=new ImageView(getActivity());
        addIcon.setImageResource(R.drawable.ic_twenty);
        SubActionButton twenty_Btn=subBuilder.setContentView(addIcon).build();

        final FloatingActionMenu fam=new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(five_Btn)
                .addSubActionView(ten_Btn)
                .addSubActionView(twenty_Btn)
                .attachTo(area_FloatingBtn)
                .build();

        five_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryData(5);
            }
        });

        ten_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryData(10);
            }
        });

        twenty_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryData(20);
            }
        });
    }

    private void setCategoryData(int distance){
        if(query_Product!=null){
            productsArrayList.clear();
            presenter.SearchProduct(query_Product,MainScreen.getLatitude(),MainScreen.getLongitude() ,distance);
        }

    }

}
