package com.example.apothecary.views.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.Brands_HorizontalRecyclerViewAdapter;
import com.example.apothecary.adapter.HomeViewPagerHeaderAdapter;
import com.example.apothecary.adapter.RecyclerHomeViewAdapterCategory;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.user.User;
import com.example.apothecary.views.view.Category.CategoryMainActivity;
import com.example.apothecary.views.view.Category.CategoryMainFragment;
import com.example.apothecary.views.view.brands.BrandsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment implements HomeView {


    private static User user;

    @BindView(R.id.home_ViewPagerHeader)
    ViewPager viewPagerNewsFeed;
    @BindView(R.id.home_RecyclerViewCategory)
    RecyclerView recyclerViewCategory;
    @BindView(R.id.home_PopularBrands_Recyclerview)
    RecyclerView recyclerViewPopularBrands;
    //@BindView(R.id.home_ViewPagerBrand)
   // ViewPager viewPagerPopularBrands;
    @BindView(R.id.home_viewAllBrands)
    TextView viewAllBrandsText;


    HomePresenter presenter;
    View view;

    FragmentManager fragmentManager ;
    CategoryMainFragment categoryMainFragment;
    BrandsFragment brandsFragment;

    GridLayoutManager manager;
    GridLayoutManager gridLayoutManager_Brands;
    LinearLayoutManager linearLayoutManager;
    HomeFragmentListener homeFragmentListener ;
    HomeViewPagerHeaderAdapter headerAdapter;

    List<ListData> newsFeedList = new ArrayList<>();

    Timer timer ;
    int currentPosition = 0 , customViewPager_Header=0;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        presenter = new HomePresenter(this);
        InitializeFragementAndObjects();

        return view;
    }

    void InitializeFragementAndObjects(){
        fragmentManager = getActivity().getSupportFragmentManager();
        categoryMainFragment = new CategoryMainFragment();
        brandsFragment = new BrandsFragment();
        presenter.getNewsFeed();
        presenter.getCategories();
        presenter.getPopularBrands();
        getArgumentsFromMainScreen();
//        userText.setText(MainScreen.getUser().getCustomerId() +" "+MainScreen.getUser().getFullname());
 //       userText.setVisibility(View.GONE);
        setViewAllBrands_Click();
    }

    private void getArgumentsFromMainScreen(){
        if(getArguments()!=null){
            user = getArguments().getParcelable("LOGINUSER_DATA_EXTRA");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeFragmentListener){
            homeFragmentListener = (HomeFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString()+" must implement Home_MealDb_ClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeFragmentListener = null;
    }

    @Override
    public void showLoading() {
        view.findViewById(R.id.shimmerNewsFeed).setVisibility(view.VISIBLE);
        view.findViewById(R.id.shimmerCategory).setVisibility(view.VISIBLE);
        view.findViewById(R.id.shimmerBrands).setVisibility(view.VISIBLE);
    }

    @Override
    public void hideLoading() {
        view.findViewById(R.id.shimmerNewsFeed).setVisibility(view.GONE);
        view.findViewById(R.id.shimmerCategory).setVisibility(view.GONE);
        view.findViewById(R.id.shimmerBrands).setVisibility(view.GONE);
    }

    @Override
    public void setNewsFeed(List<ListData> newsFeed) {

        for(ListData item : newsFeed ){
            newsFeedList.add(item);
        }

        headerAdapter = new HomeViewPagerHeaderAdapter(newsFeed, getActivity());
        viewPagerNewsFeed.setAdapter(headerAdapter);
        viewPagerNewsFeed.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();
        setCreateSlideShow();

        headerAdapter.setOnItemClickListener((v, position) -> {
            //Toast.makeText(this, new.get(position).getStrCategory(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void setCategory(List<ListData> category) {
        RecyclerHomeViewAdapterCategory homeAdapterCategory = new RecyclerHomeViewAdapterCategory(category, getActivity());
        recyclerViewCategory.setAdapter(homeAdapterCategory);
        GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapterCategory.notifyDataSetChanged();

        homeAdapterCategory.setOnItemClickListener(new RecyclerHomeViewAdapterCategory.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int catID = category.get(position).getId();
                int pos = position;

                //Intent intent = new Intent(getActivity(), CategroryActivity.class);
                Intent intent = new Intent(getActivity(), CategoryMainActivity.class);
                intent.putExtra("EXTRA_POSITION",position);
                intent.putExtra("EXTRA_CATEGORY",(Serializable) category);
                startActivity(intent);

             //   homeFragmentListener.sendCategoryData(category,category.get(position).getId(),position);
            //    getActivity().getSupportFragmentManager()
             //           .beginTransaction()
             //           .replace(R.id.fragmentContainer,categoryMainFragment,"Category_Main")
              //          .addToBackStack(null)
               //         .commit();
            }
        });

    }

    @Override
    public void setPopularBrands(List<Brands> brands) {


        Brands_HorizontalRecyclerViewAdapter brands_horizontalRecyclerViewAdapter = new Brands_HorizontalRecyclerViewAdapter(brands,getActivity());
        recyclerViewPopularBrands.setAdapter(brands_horizontalRecyclerViewAdapter);
        gridLayoutManager_Brands = new GridLayoutManager(
                getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerViewPopularBrands.setLayoutManager(gridLayoutManager_Brands);
        recyclerViewPopularBrands.setNestedScrollingEnabled(true);
        brands_horizontalRecyclerViewAdapter.notifyDataSetChanged();

        brands_horizontalRecyclerViewAdapter.setOnItemClickListener(new Brands_HorizontalRecyclerViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getActivity(),"Brand: "+brands.get(position).getBrandName(),Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Title", message);
    }

    private  void setCreateSlideShow(){
     final Handler handler = new Handler();
     final Runnable runnable = new Runnable() {
         @Override
         public void run() {
          if(currentPosition==newsFeedList.size())
                 currentPosition=0;
            viewPagerNewsFeed.setCurrentItem(currentPosition++,true);
         }
     };

     timer = new Timer();
     timer.schedule(new TimerTask() {
         @Override
         public void run() {
             handler.post(runnable);
         }
     },250,3000);

    }

    private void setViewAllBrands_Click(){
        viewAllBrandsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,brandsFragment,"Brand_Fragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
    }



    public interface HomeFragmentListener {
        void sendCategoryData(List<ListData> categories , int id ,int position);
    }
}
