package com.example.apothecary.views.view.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter;
import com.example.apothecary.adapter.RecyclerViewProductsByCategoryAdapter2;
import com.example.apothecary.models.product.Product;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoryView {

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

    View view;

    AlertDialog.Builder descDialog;

    private static RecyclerViewProductsByCategoryAdapter adapter;
    private static RecyclerViewProductsByCategoryAdapter2 adapter2;
    //ArrayList<Products> productsArrayList = new ArrayList<>();
    List<Stocks> stocksArrayList;// = new ArrayList<>();
    List<Stock> StockList;
    List<Product> ProductList;
    List<Products> productsArrayList;
    LinearLayoutManager manager;

    static CategoryPresenter presenter;

    //private Menu menu;
    CategoriesFragmentListener listener;
    //SearchView searchView;




    boolean isScrolling=false , isLoading=false, isLastPage=false;

    int currentItems , totalItems ,scrolledOutItems;

    private static final int START_PAGE=1;
    private int  TOTAL_PAGES,CURRENT_PAGE;
    private  String NEXT_PAGE=null;

    static int CategoryId , Tab_Positon;


    //static int pageCount=1,totalPages;

    List<Product> ProductArrayList ;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories,container,false);
        view.findViewById(R.id.shimmerProducts_Categories).setVisibility(View.VISIBLE);
        ButterKnife.bind(this, view);
        stocksArrayList = new ArrayList<>();
        StockList = new ArrayList<>();
        productsArrayList = new ArrayList<>();
        ProductList = new ArrayList<>();
        ProductArrayList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        getArgumentsForFragment();
        return view;
    }

    private void getArgumentsForFragment(){

        if(getArguments()!=null){
       //    Tab_Positon = getArguments().getInt("EXTRA_DATA_TABPOSITION");
            CategoryId =  getArguments().getInt("EXTRA_DATA_ID");
            textCategory.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategory);
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategoryBg);
            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString("EXTRA_DATA_NAME"))
                    .setMessage(getArguments().getString("EXTRA_DATA_DESC"));

             presenter = new CategoryPresenter(this);
                CURRENT_PAGE = START_PAGE;

//                ProductArrayList.set(Tab_Positon,null);
            //presenter.getStocksByCategory(CategoryId);
            //presenter.getProductsByCategory(CategoryId,31.528808,74.368788,10);
            presenter.getProductsByCategory(CategoryId, MainScreen.getLatitude(),MainScreen.getLongitude(),10);

        }

        setFloatingActionButton();

    }


    public void setTabPosition(int tab_position){
        Tab_Positon = tab_position;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        view.findViewById(R.id.shimmerProducts_Categories).setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUpdateLoading() {

    }

    @Override
    public void hideUpdateLoading() {

    }

    @Override
    public void setProducts(Product products) {

        if(products!=null){

           ProductList.add(products);

          //  ProductArrayList.set(Tab_Positon,products);



            /*CURRENT_PAGE = products.getCurrentPage();
            TOTAL_PAGES = products.getLastPage();
            if(products.getNextPageUrl()==null){
                NEXT_PAGE = products.getLastPageUrl();
            }else{
                NEXT_PAGE = products.getNextPageUrl();
            } */




            productsArrayList.addAll((Collection<? extends Products>) products.getData());

            adapter = new RecyclerViewProductsByCategoryAdapter(getActivity(),productsArrayList);
            recyclerView.setLayoutManager(manager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            adapter.setOnItemClickListener(new RecyclerViewProductsByCategoryAdapter.ClickListener() {

                @Override
                public void onClick( View view, int position) {
                    TextView Id =  view.findViewById(R.id.productId2);



                    //Toasty.info(getActivity(),position, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                    intent.putExtra("EXTRA_PRODUCT_DETAILS_ID",Id.getText().toString());
                    //intent.putExtra("USER_DATA_PRODUCTDETAILS",MainScreen.getUser());
                    startActivity(intent);

                }

            });



            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        //getStocksData(NEXT_PAGE);
                        //fetchData(NEXT_PAGE);
                        progressBar.setVisibility(View.GONE);
                    }


                }
            });

        }



    }

    @Override
    public void setStocks(Stock stock) {

        StockList.add(stock);

        CURRENT_PAGE = stock.getCurrentPage();
        TOTAL_PAGES = stock.getLastPage();
        if(stock.getNextPageUrl()==null){
            NEXT_PAGE = stock.getLastPageUrl();
        }else{
            NEXT_PAGE = stock.getNextPageUrl();
        }

        //TOTAL_PAGES = stocks.getLastPage();
        //CURRENT_PAGE = START_PAGE;
       // Stocks erm = stock.getData().get(0);
        stocksArrayList.addAll((Collection<? extends Stocks>) stock.getData());


    adapter2 = new RecyclerViewProductsByCategoryAdapter2(getActivity(),stocksArrayList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        //adapter2.setOnItemClickListener((view,posi));

        adapter2.setOnItemClickListener(new RecyclerViewProductsByCategoryAdapter2.ClickListener() {

            @Override
            public void onClick( View view, int position) {
                TextView Id =  view.findViewById(R.id.productId2);
                int pos = adapter2.getViewHolderPosition();

                //Toasty.info(getActivity(),CategoryMainFragment.getCurrentTabPosition(), Toast.LENGTH_LONG).show();

               // listener.sendStockDetailsToDetailedActivity(Integer.valueOf(Id.getText().toString()));

                //Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                //intent.putExtra("EXTRA_PRODUCT_DETAILS_ID",Id.getText().toString());
               // startActivity(intent);

            }

        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    //getStocksData(NEXT_PAGE);
                    //fetchData(NEXT_PAGE);
                    progressBar.setVisibility(View.GONE);
                }


            }
        });
    }

    @Override
    public void setUpdatedProducts(Product products) {

    }


    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CategoriesFragment.CategoriesFragmentListener){
            listener = (CategoriesFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement CategoriesFragment_ClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public interface CategoriesFragmentListener{
        void sendProductsAdapter(RecyclerViewProductsByCategoryAdapter adapter);
        void sendStocksAdapter(RecyclerViewProductsByCategoryAdapter2 adapter);
        void sendStockDetailsToDetailedActivity(int stockId);
        void sendProductDetailsToDetailedActivity(int productId);

    }


    public void getStocksData(String URL){

        if(URL==null){

            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        Call<Stock> productCall =  Utils.getApothecaryApi().getStocks(URL);
        productCall.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if(response.isSuccessful() && response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    if(response.body().getData()!=null){
                        //view.setUpdatedStocks(response.body());
                        NEXT_PAGE = response.body().getNextPageUrl();
                        List<Stocks> stocksList = response.body().getData();
                        int sizeOfList = stocksList.size();
                        for(int position=0 ; position<sizeOfList ; position++ ){
                            stocksArrayList.add(stocksList.get(0));
                            adapter2.notifyDataSetChanged();
                        }
                        //for(Stocks item : response.body().getData()){
                        //    stocksArrayList.add(item);
                       // }

                    }

                }else{
                    //view.onErrorLoading(response.message());
                    Toasty.error(getActivity(),response.message(),Toasty.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toasty.error(getActivity(),t.getLocalizedMessage(),Toasty.LENGTH_LONG).show();
                //view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("CLOSE", (dialog, which) -> dialog.dismiss());
        descDialog.show();
    }


    private void fetchData(String URL){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // presenter.getStocksData(CategoryId,CURRENT_PAGE,TOTAL_PAGES);
                //presenter.getStocksData(NEXT_PAGE);
                //getStocksData(URL);
            }
        }, 2000);
    }

    private void setFloatingActionButton() {
        final ImageView floating_icon = new ImageView(getActivity());
        floating_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_area_primary));
        final com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton area_FloatingBtn =
                new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                        .setContentView(floating_icon).build();

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

        //area_FloatingActionButton
    }

    private void setCategoryData(int distance){
        ProductList.clear();
        productsArrayList.clear();
        presenter.getProductsByCategory(CategoryId, MainScreen.getLatitude(),MainScreen.getLongitude(),distance);
    }

}
