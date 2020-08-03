package com.example.apothecary.views.view.orderdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.adapter.OrderDetails_RecyclerView_Adapter;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.userorderdetails.OrderDetail;
import com.example.apothecary.views.mainscreen.MainScreen;
import com.example.apothecary.views.view.cart.CartFragment;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.example.apothecary.views.view.search.SearchFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailView {

    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private static final String USER_ID_KEY ="USER_ID";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.orderdetails_ScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.orderdetails_Image)
    ImageView productImage;
    @BindView(R.id.orderdetails_OrderNo)
    TextView OrderNo;
    @BindView(R.id.orderdetails_ItemCount)
    TextView ItemCount;
    @BindView(R.id.orderdetails_TotalAmount)
    TextView TotalAmount;
    @BindView(R.id.orderdetails_date)
    TextView Date;
    @BindView(R.id.orderdetails_time)
    TextView Time;
    @BindView(R.id.orderdetails_Recyclerview)
    RecyclerView orderdetail_Recyclerview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    LinearLayoutManager manager;

    int userId;

    OrderDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        presenter = new OrderDetailPresenter(this);
        editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        preferences =  getSharedPreferences(FILE_NAME,MODE_PRIVATE);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("ORDER_ID_EXTRA",0);

        userId = preferences.getInt(USER_ID_KEY,0);
        presenter.getOrderDetails(orderId,userId);

    }

    @Override
    public void showLoading() {
            progressBar.setVisibility(View.VISIBLE);
            findViewById(R.id.shimmerOrderDetails).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.shimmerOrderDetails).setVisibility(View.GONE);
    }

    @Override
    public void setOrderDetails(List<OrderDetail> orderDetails) {


        Picasso.get().load(getImageUrl(orderDetails.get(0).getPrescriptionImage())).placeholder(R.drawable.ic_circle2).into(productImage);
        OrderNo.setText("Order# URO-"+orderDetails.get(0).getUserOrderId());
        ItemCount.setText("Total Items: "+orderDetails.get(0).getTotalItem());
        TotalAmount.setText(Double.toString(orderDetails.get(0).getTotalAmount()));
        Date.setText("Order# URO-"+orderDetails.get(0).getDate());
        Time.setText("Order# URO-"+orderDetails.get(0).getTime());


        OrderDetails_RecyclerView_Adapter adapter = new OrderDetails_RecyclerView_Adapter(this,orderDetails);
        orderdetail_Recyclerview.setAdapter(adapter);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        orderdetail_Recyclerview.setLayoutManager(manager);
        orderdetail_Recyclerview.setNestedScrollingEnabled(true);
        adapter.notifyDataSetChanged();

        setupActionBar();
    }

    private void setupActionBar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_primary_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailedavtivity_searc_menu, menu);
        Intent intent = new Intent(OrderDetailsActivity.this, MainScreen.class);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menu_detail_search:{

                        intent.putExtra("PRODUCT_DETAILS_BACK_ID",2);
                        startActivity(intent);
                        finish();
                    }
                    break;

                    case R.id.menu_detail_cart:{
                        intent.putExtra("PRODUCT_DETAILS_BACK_ID",3);
                        startActivity(intent);
                         finish();
                    }
                    break;

                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onErrorLoading(String message) { Toasty.normal(OrderDetailsActivity.this,message,Toasty.LENGTH_LONG); }
}
