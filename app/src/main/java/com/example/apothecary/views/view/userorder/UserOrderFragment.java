package com.example.apothecary.views.view.userorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
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
import android.widget.RelativeLayout;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.HomeViewPagerHeaderAdapter;
import com.example.apothecary.adapter.Order_Recyclerview_Adapter;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.models.user_order.Datum;
import com.example.apothecary.models.user_order.UserOrder;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.view.orderdetails.OrderDetailsActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UserOrderFragment extends Fragment implements UserOrderView {

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private static final String USER_ID_KEY ="USER_ID";

    @BindView(R.id.order_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_Recyclerview)
    RecyclerView orderRecyclerView;
    @BindView(R.id.order_ScrollProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.order_NestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.order_CardView)
    CardView logoCardView;
    @BindView(R.id.order_AnimationBackground)
    RelativeLayout animation_BG;
    @BindView(R.id.order_ViewPagerHeader)
    ViewPager viewPagerNewsFeed;
    @BindView(R.id.order_RefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    View view;
    UserOrderPresenter presenter;
    LinearLayoutManager linearLayoutManager;
    HomeFragment homeFragment;

    List<UserOrder> orderList;
    List<Datum> order_ArrayList;

    Order_Recyclerview_Adapter adaptar;
    HomeViewPagerHeaderAdapter headerAdapter;

    List<ListData> newsFeedList = new ArrayList<>();

    Timer timer ;
    int currentPosition = 0 , customViewPager_Header=0;

    UserOrder ORDER;

    private static String NEXT_PAGE_URL;
    static int CURRENT_PAGE;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;


    Menu menu;

    static int userId;

    public UserOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_order, container, false);
        ButterKnife.bind(this,view);
        editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        preferences =  getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        userId = preferences.getInt(USER_ID_KEY,0);
        setHasOptionsMenu(true);
        order_ArrayList = new ArrayList<>();
        orderList = new ArrayList<>();
        presenter = new UserOrderPresenter(this);
        homeFragment = new HomeFragment();
        setToolbar();
        SwipeRefreshLayoutListener();
        presenter.getUserOrder(userId);
        presenter.getNewsFeed();
        return view;
    }


    @Override
    public void showLoading() {
        view.findViewById(R.id.shimmerOrder).setVisibility(View.VISIBLE);
        view.findViewById(R.id.shimmerNewsFeed).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        view.findViewById(R.id.shimmerOrder).setVisibility(View.GONE);
        view.findViewById(R.id.shimmerNewsFeed).setVisibility(View.GONE);
    }

    @Override
    public void showUpdatingLoading() {progressbar.setVisibility(View.VISIBLE); }

    @Override
    public void hideUpdatingLoading() {progressbar.setVisibility(View.GONE); }

    @Override
    public void setUserOrder(UserOrder order) { setOrder(order);}

    @Override
    public void setUpdatedUserOrder(UserOrder order) { setOrder(order); }

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
    }

    private void setOrder(UserOrder order){

        if(order.getData().size()==0){
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.GONE);
            orderRecyclerView.setVisibility(View.GONE);
            animation_BG.setVisibility(View.VISIBLE);
        }else{

            swipeRefreshLayout.setRefreshing(false);
            animation_BG.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            orderRecyclerView.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.VISIBLE);

            ORDER = order;

            if(order.getData()!=null){
                NEXT_PAGE_URL = order.getNextPageUrl();
                CURRENT_PAGE=order.getCurrentPage();

                order_ArrayList.addAll(order.getData());

                adaptar= new Order_Recyclerview_Adapter(getActivity(),order_ArrayList);
                orderRecyclerView.setAdapter(adaptar);
                linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                orderRecyclerView.setLayoutManager(linearLayoutManager);
                orderRecyclerView.setNestedScrollingEnabled(false);
                orderRecyclerView.setClipToPadding(false);
                adaptar.notifyDataSetChanged();

                adaptar.setOnItemClickListener(new Order_Recyclerview_Adapter.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                    }

                    @Override
                    public void onDetailClick(int position) {
                        moveToOrderDetailActivity(order.getData().get(position).getOrderId());
                    }
                });

                Recyclerview_OnScroll();
                NestedScrollView_OnScroll();

            }

        }



    }

    private void moveToOrderDetailActivity(int orderId){
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("ORDER_ID_EXTRA",orderId);
        startActivity(intent);
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

    private void Recyclerview_OnScroll(){
        orderRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if(ORDER.getNextPageUrl()!=null){
                    presenter.getUpdatedUserOrder(ORDER.getNextPageUrl());
                }else{
                    progressbar.setVisibility(View.GONE);
                }

            }
        }, 2500);
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

    private void SwipeRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                order_ArrayList.clear();
                adaptar.notifyDataSetChanged();
                presenter.getUserOrder(userId);

            }
        });
    }

}
