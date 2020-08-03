package com.example.apothecary.views.view.cart;

import android.app.ProgressDialog;
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
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.CartItem_RecyclerView_Adaptar;
import com.example.apothecary.models.cart.Cart;
import com.example.apothecary.models.cart.Datum;
import com.example.apothecary.models.cart.UpdateCart;
import com.example.apothecary.views.view.home.HomeFragment;
import com.example.apothecary.views.view.checkout.CheckOutActivity;
import com.example.apothecary.views.view.products.ProductsDetailActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartView {


    private static final String FILE_NAME ="Apothecary";

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private static final String USER_ID_KEY ="USER_ID";

    private static int userId;

    View view;
    ProgressDialog pDialog;
     CartPresenter presenter;

    @BindView(R.id.cart_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.cart_Recyclerview)
    RecyclerView cartRecyclerView;
    @BindView(R.id.cart_ScrollProgressBar)
    SpinKitView progressbar;
    @BindView(R.id.cart_NestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.cart_CardView)
    CardView logoCardView;
    @BindView(R.id.cart_AnimationBackground)
    RelativeLayout animationBG;
    @BindView(R.id.Cart_CheckOut_Btn)
    Button checkOut_Btn;
    @BindView(R.id.Cart_TotalCartAmount)
    TextView TotalAmount_Text;
    @BindView(R.id.cart_CartCount)
    TextView cartCount;
    @BindView(R.id.cart_RemoveAll_Button)
    ImageView EmptyCart_Btn;
    @BindView(R.id.cart_SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    LinearLayoutManager linearLayoutManager;
    HomeFragment homeFragment;

      List<Cart> cartList;
     List<Datum> cart_ArrayList;

     CartItem_RecyclerView_Adaptar adaptar;
    Cart CART;

    private static String NEXT_PAGE_URL;
    static int CURRENT_PAGE;

    boolean isScrolling=false;

    int currentItems , totalItems, scrolledOutItems;

     List<Datum> confirm_CartList;


    Menu menu;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this,view);
         editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        preferences =  getActivity().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

        initDialog();
        userId = preferences.getInt(USER_ID_KEY,0);

        setHasOptionsMenu(true);
        cartList = new ArrayList<>();
        cart_ArrayList = new ArrayList<>();
        confirm_CartList =new ArrayList<>();

        homeFragment = new HomeFragment();
        setToolbar();

        presenter = new CartPresenter(this);
        presenter.getCartList(userId);

        EmptyCart_ClickEvent();
        setCheckOutBtn_ClickEvent();
        SwipeRefresh();
        checkOut_Btn.setText("CHECK OUT");

        return view;
    }




    @Override
    public void showLoading() { view.findViewById(R.id.shimmerCart).setVisibility(View.VISIBLE); }

    @Override
    public void hideLoading() { view.findViewById(R.id.shimmerCart).setVisibility(View.GONE); }

    @Override
    public void showUpdatingLoading() { progressbar.setVisibility(View.VISIBLE); }

    @Override
    public void hideUpdatingLoading() { progressbar.setVisibility(View.GONE); }

    @Override
    public void showRemoveLoading() { showpDialog(); }

    @Override
    public void hideRemoveLoading() { hidepDialog();}

    @Override
    public void setCart(Cart cart) { SetCartData(cart); }

    @Override
    public void setUpdatedCart(Cart cart) { SetCartData(cart); }

    @Override
    public void setAddCart(UpdateCart cart) { Toast.makeText(getActivity(),"Quantity Added (+1)",Toast.LENGTH_SHORT).show(); }

    @Override
    public void setSubCart(UpdateCart cart) { Toast.makeText(getActivity(),"Quantity Deducted (-1)",Toast.LENGTH_SHORT).show();}

    @Override
    public void setRemoveCart(int responseCode) {
        if(responseCode==204){ Toast.makeText(getActivity(),"Item Removed Successfully from Cart",Toast.LENGTH_SHORT).show(); }
    }

    @Override
    public void setEmptyCart(int responseCode) {
        if(responseCode==204){
            cartCount.setText("(0)");
            TotalAmount_Text.setText("0.0");
            nestedScrollView.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.GONE);
            animationBG.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onErrorLoading(String message) { Utils.showDialogMessage(getActivity(), "Title", message); }

    private void SetCartData(Cart cart){

        if(cart.getData().size()==0){
            refreshLayout.setRefreshing(false);
            TotalAmount_Text.setText("0.0");
            nestedScrollView.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.GONE);
            animationBG.setVisibility(View.VISIBLE);
        }else{
            refreshLayout.setRefreshing(false);
            nestedScrollView.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.VISIBLE);
            animationBG.setVisibility(View.GONE);


            CART = cart;
            double Total_Amount=0.0;
            for(int i=0;i<cart.getData().size();i++){
                Total_Amount+=cart.getData().get(i).getTotalPrice();
            }

            TotalAmount_Text.setText(Double.toString(Total_Amount));

            if(cart.getData().size()>0){
                NEXT_PAGE_URL = cart.getNextPageUrl();
                CURRENT_PAGE=cart.getCurrentPage();

                cart_ArrayList.addAll(cart.getData());

                adaptar= new CartItem_RecyclerView_Adaptar(cart_ArrayList,getActivity());
                cartRecyclerView.setAdapter(adaptar);
                linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                cartRecyclerView.setLayoutManager(linearLayoutManager);
                cartRecyclerView.setNestedScrollingEnabled(false);
                cartRecyclerView.setClipToPadding(false);
                adaptar.notifyDataSetChanged();

                cartCount.setText("("+cart_ArrayList.size()+")");
                RecyclerView_ClickEvents();

                Recyclerview_OnScroll();
                NestedScrollView_OnScroll();

            }

        }

    }

    private void RecyclerView_ClickEvents() {

        adaptar.setOnItemClickListener(new CartItem_RecyclerView_Adaptar.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int productId = cart_ArrayList.get(position).getProductId();
                Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                intent.putExtra("EXTRA_PRODUCT_DETAILS_ID",  productId);
                startActivity(intent);
            }

            @Override
            public void onAddBtn_Click(int position) {
                int cartId = cart_ArrayList.get(position).getCartId();

                double totalAmount = Double.valueOf(TotalAmount_Text.getText().toString());

                if(cart_ArrayList.get(position).getQuantity()<10){
                    cart_ArrayList.get(position).setQuantity((cart_ArrayList.get(position).getQuantity()+1));
                    double newTotalPrice=0.0;

                    switch (cart_ArrayList.get(position).getCartType()){
                        case 0:
                            newTotalPrice =cart_ArrayList.get(position).getTotalPrice()+cart_ArrayList.get(position).getUnitPrice();
                            totalAmount = totalAmount+cart_ArrayList.get(position).getUnitPrice();
                            break;

                        case 1:
                            newTotalPrice =cart_ArrayList.get(position).getTotalPrice()+cart_ArrayList.get(position).getLeafPrice();
                            totalAmount = totalAmount+cart_ArrayList.get(position).getLeafPrice();
                            break;

                        case 2:
                            newTotalPrice =cart_ArrayList.get(position).getTotalPrice()+cart_ArrayList.get(position).getBoxPrice();
                            totalAmount = totalAmount+cart_ArrayList.get(position).getBoxPrice();
                            break;
                    }

                    cart_ArrayList.get(position).setTotalPrice(newTotalPrice);
                    adaptar.notifyDataSetChanged();
                    TotalAmount_Text.setText(Double.toString(totalAmount));

                    presenter.updateAddCart(cartId);
                }
                else if(cart_ArrayList.get(position).getQuantity()>=10){
                    Toast.makeText(getActivity(),"Maximum 10 items can be selected",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onSubBtn_Click(int position) {
                int cartId = cart_ArrayList.get(position).getCartId();

                double totalAmount = Double.valueOf(TotalAmount_Text.getText().toString());

                cart_ArrayList.get(position).setQuantity((cart_ArrayList.get(position).getQuantity()-1));
                double newTotalPrice=0.0;
                switch (cart_ArrayList.get(position).getCartType()){
                    case 0:
                        newTotalPrice =(cart_ArrayList.get(position).getTotalPrice()-cart_ArrayList.get(position).getUnitPrice());
                        totalAmount = totalAmount-cart_ArrayList.get(position).getUnitPrice();
                        break;

                    case 1:
                        newTotalPrice =(cart_ArrayList.get(position).getTotalPrice()-cart_ArrayList.get(position).getLeafPrice());
                        totalAmount = totalAmount-cart_ArrayList.get(position).getLeafPrice();
                        break;

                    case 2:
                        newTotalPrice =(cart_ArrayList.get(position).getTotalPrice()-cart_ArrayList.get(position).getBoxPrice());
                        totalAmount = totalAmount-cart_ArrayList.get(position).getBoxPrice();
                        break;
                }

                cart_ArrayList.get(position).setTotalPrice(newTotalPrice);
                TotalAmount_Text.setText(Double.toString(totalAmount));
                adaptar.notifyDataSetChanged();

                presenter.updateSubCart(cartId);
            }

            @Override
            public void onRemoveBtn_Click(int position) {

                if(cart_ArrayList.size()==0){
                    TotalAmount_Text.setText("0.0");

                }else{

                    int cartId = cart_ArrayList.get(position).getCartId();
                    double totalPrice_Product = cart_ArrayList.get(position).getTotalPrice();
                    double totalAmount = Double.valueOf(TotalAmount_Text.getText().toString());

                    totalAmount = totalAmount-totalPrice_Product;

                    TotalAmount_Text.setText(Double.toString(totalAmount));

                    presenter.removeCart(cartId);
                    Datum cartItem = cart_ArrayList.get(position);
                    cart_ArrayList.remove(position);


                    if(confirm_CartList.size()!=0 && confirm_CartList.contains(cartItem)){
                        confirm_CartList.remove(cartItem);
                        checkOut_Btn.setText("CHECK OUT ("+confirm_CartList.size()+")");
                    }


                    if(cart_ArrayList.size()==0){
                        TotalAmount_Text.setText("0.0");
                        cartCount.setText("(0)");
                        nestedScrollView.setVisibility(View.GONE);
                        cartRecyclerView.setVisibility(View.GONE);
                        animationBG.setVisibility(View.VISIBLE);
                    }else{
                        cartCount.setText("("+cart_ArrayList.size()+")");
                        nestedScrollView.setVisibility(View.VISIBLE);
                        cartRecyclerView.setVisibility(View.VISIBLE);
                        animationBG.setVisibility(View.GONE);
                    }

                    adaptar.notifyDataSetChanged();

                }

            }

            @Override
            public void onAddCheckBox_Click(int position) {

                confirm_CartList.add(cart_ArrayList.get(position));
                checkOut_Btn.setText("CHECK OUT ("+confirm_CartList.size()+")");

            }

            @Override
            public void onRemoveCheckBox_Click(int position) {

                Datum cartItem = cart_ArrayList.get(position);

                if(!confirm_CartList.isEmpty() || confirm_CartList.size()!=0){

                    if(confirm_CartList.contains(cartItem)){
                        confirm_CartList.remove(cartItem);
                        checkOut_Btn.setText("CHECK OUT ("+confirm_CartList.size()+")");
                    }

                }
            }
        });

    }

    private void setCheckOutBtn_ClickEvent(){

        checkOut_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(confirm_CartList.size()!=0){

                    if(confirm_CartList.size()<=5){
                        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                        intent.putParcelableArrayListExtra("CONFIRM_CART_LIST", (ArrayList<? extends Parcelable>) confirm_CartList);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(),"Note: You can only order Max. 5 Products",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getActivity(),"Please..! Select items to place order.",Toast.LENGTH_LONG).show();
                }

            }
        });





    }

    private void Recyclerview_OnScroll(){
        cartRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if(CART.getNextPageUrl()!=null){
                    presenter.getUpdatedCartList(CART.getNextPageUrl());
                }else{
                    progressbar.setVisibility(View.GONE);
                }

            }
        }, 2500);
    }

    private void EmptyCart_ClickEvent(){
        EmptyCart_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.emptyCart(userId);
                cart_ArrayList.clear();
                confirm_CartList.clear();
                adaptar.notifyDataSetChanged();
            }
        });
    }

    protected void initDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.msg_removing));
        pDialog.setCancelable(true);
    }

    protected void showpDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() { if (pDialog.isShowing()) pDialog.dismiss(); }

    private void setToolbar(){
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
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
    }

    private void SwipeRefresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cart_ArrayList.size()!=0){
                    cart_ArrayList.clear();
                    adaptar.notifyDataSetChanged();
                    presenter.getCartList(userId);
                }
            }

        });
    }



}
