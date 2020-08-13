package com.example.apothecary.views.view.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter2;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryMainActivity extends AppCompatActivity implements CategoryView {

    @BindView(R.id.toolbar_CategoryMain)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.imageCardView)
    CardView logoCardView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.productsScrollProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageCategory)
    ImageView imageCategory;
    @BindView(R.id.imageCategoryBg)
    ImageView imageCategoryBg;
    @BindView(R.id.textCategory)
    TextView textCategory;
    @BindView(R.id.category_NestedScrollView)
    NestedScrollView nestedScrollView;
   // @BindView(R.id.floatingButton_CategoryMain)
    FloatingActionButton area_FloatingActionButton;

    AlertDialog.Builder descDialog;

    Menu menu;

    private static List<ListData> categories;
    private static int category_position;


    private static RecyclerViewProductsByCategoryAdapter adapter;
    private static RecyclerViewProductsByCategoryAdapter2 adapter2;
    //ArrayList<Products> productsArrayList = new ArrayList<>();

    List<Products> productsArrayList;

    LinearLayoutManager linearLayoutManager;

     CategoryPresenter presenter;


    Product PRODUCT;
    Stock STOCK;


    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;

    private static int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);
        ButterKnife.bind(this);
        presenter = new CategoryPresenter(this);
        setToolbar();
        //setHasOptionsMenu(true);
        setupTabLayout();
        setTab_Listener();
        //setFloatingActionButton();
        productsArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //setFloatingActionButton();
        //area_FloatingActionButton.setVisibility(View.INVISIBLE);
    }


    private void setToolbar(){
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
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


                adapter.getFilter().filter(newText);
                return false;

            }
        });

    }

    private void setupTabLayout() {
        Intent intent = getIntent();
        categories  =
                ( List<ListData>) intent.getSerializableExtra("EXTRA_CATEGORY");
        category_position = intent.getIntExtra("EXTRA_POSITION", 0);

        for (int position=0 ; position<categories.size();position++){
            tabLayout.addTab(tabLayout.newTab().setText(categories.get(position).getDataName()));
        }
        setTabContent(category_position);

    }

    private void setTabContent(int position){
        tabPosition = position;
        tabLayout.getTabAt(position).select();
        tabLayout.setScrollPosition(position, 0, true);

       // CategoryId =  getArguments().getInt("EXTRA_DATA_ID");
        textCategory.setText(categories.get(position).getDataName());
        Picasso.get().load(getImageUrl(categories.get(position).getImage()))
                .into(imageCategory);
        Picasso.get().load(getImageUrl(categories.get(position).getImage()))
                .into(imageCategoryBg);
        descDialog = new AlertDialog.Builder(this)
                .setTitle(categories.get(position).getDataName())
                .setMessage(categories.get(position).getDescription());

        presenter.getProductsByCategory(categories.get(position).getId(), MainScreen.getLatitude(),MainScreen.getLongitude(),10);
    }

    private void setTab_Listener(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productsArrayList.clear();
                setTabContent(tab.getPosition());
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




    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }
    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("CLOSE", (dialog, which) -> dialog.dismiss());
        descDialog.show();
    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerProducts_Categories).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerProducts_Categories).setVisibility(View.GONE);
    }

    @Override
    public void showUpdateLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUpdateLoading() { progressBar.setVisibility(View.GONE); }

    @Override
    public void setProducts(Product products) { setProductsData(products); }


    private void setProductsData(Product products){
        if(products!=null) {

            PRODUCT = products;

            productsArrayList.addAll((Collection<? extends Products>) products.getData());

            adapter = new RecyclerViewProductsByCategoryAdapter(this, productsArrayList);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            adapter.setOnItemClickListener(new RecyclerViewProductsByCategoryAdapter.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    TextView Id = view.findViewById(R.id.productId2);
                    int productId =   productsArrayList.get(position).getProductId();
                    Intent intent = new Intent(CategoryMainActivity.this, ProductsDetailActivity.class);
                    intent.putExtra("EXTRA_PRODUCT_DETAILS_ID", productId ); //Id.getText().toString());
                    startActivity(intent);

                }

            });

            Recyclerview_OnScroll();
            NestedScrollView_OnScroll();

        }
    }

    @Override
    public void setStocks(Stock stocks) {

    }

    @Override
    public void setUpdatedProducts(Product products) {
        setProductsData(products);
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Error ", message);
    }

    private void Recyclerview_OnScroll(){
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    //presenter.getUpdatedWishList(FAVOURITES.getNextPageUrl());
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

                        progressBar.setVisibility(View.VISIBLE);
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
                if(PRODUCT.getNextPageUrl()!=null){
                    presenter.getUpdatedProductsByCategory(PRODUCT.getNextPageUrl());
                }else{
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, 2500);
    }

    private void setFloatingActionButton() {
        final ImageView floating_icon = new ImageView(this);
        floating_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_area_radar));
        final com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton area_FloatingBtn =
                new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                        .setContentView(floating_icon).build();

        SubActionButton.Builder subBuilder =  new SubActionButton.Builder(this);

        ImageView twoIcon=new ImageView(this);
        twoIcon.setImageResource(R.drawable.ic_two);
        SubActionButton two_Btn=subBuilder.setContentView(twoIcon).build();

        ImageView fiveIcon=new ImageView(this);
        fiveIcon.setImageResource(R.drawable.ic_five);
        SubActionButton five_Btn=subBuilder.setContentView(fiveIcon).build();

        ImageView tenIcon=new ImageView(this);
        tenIcon.setImageResource(R.drawable.ic_ten);
        SubActionButton ten_Btn=subBuilder.setContentView(tenIcon).build();

        ImageView twentyIcon=new ImageView(this);
        twentyIcon.setImageResource(R.drawable.ic_twenty);
        SubActionButton twenty_Btn=subBuilder.setContentView(twentyIcon).build();

        final FloatingActionMenu fam=new FloatingActionMenu.Builder(this)
                .addSubActionView(two_Btn)
                .addSubActionView(five_Btn)
                .addSubActionView(ten_Btn)
                .addSubActionView(twenty_Btn)
                .attachTo(area_FloatingBtn)
                .build();

        two_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryData(2);
            }
        });

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

        //area_FloatingActionButton
    }

    private void setCategoryData(int distance){
        //ProductList.clear();
        productsArrayList.clear();
        presenter.getProductsByCategory(categories.get(tabPosition).getId(), MainScreen.getLatitude(),MainScreen.getLongitude(),distance);
    }

}
