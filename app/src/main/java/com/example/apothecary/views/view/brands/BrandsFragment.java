package com.example.apothecary.views.view.brands;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.adapter.Brands_HorizontalRecyclerViewAdapter;
import com.example.apothecary.adapter.OtherBrands_Adaptar;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.views.view.home.HomeFragment;

import java.util.List;


public class BrandsFragment extends Fragment  implements BrandsView{

    View view;
    BrandsPresenter presenter;

    @BindView(R.id.brandsImage_Recyclerview)
    RecyclerView imageRecyclerView;
    @BindView(R.id.brandsName_Recyclerview)
    RecyclerView nameRecyclerView;
    @BindView(R.id.brands_Toolbar)
    Toolbar toolbar;
    @BindView(R.id.brands_CardView)
    CardView logoCardView;

    Menu menu;

    GridLayoutManager gridLayoutManager_OtherBrands;
    GridLayoutManager gridLayoutManager_PopularBrands;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    Brands_HorizontalRecyclerViewAdapter image_Adapter;
    OtherBrands_Adaptar name_Adapter;

    HomeFragment homeFragment;

    public BrandsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_brands, container, false);
        //view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        homeFragment = new HomeFragment();
        presenter = new BrandsPresenter(this);
        presenter.getPopularBrands();
        presenter.getOtherBrands();
        setToolbar();
        return view;
    }

    @Override
    public void showLoading() {
        view.findViewById(R.id.popular_shimmer).setVisibility(View.VISIBLE);
        view.findViewById(R.id.other_shimmer).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        view.findViewById(R.id.popular_shimmer).setVisibility(View.GONE);
        view.findViewById(R.id.other_shimmer).setVisibility(View.GONE);
    }

    @Override
    public void setOtherBrands(List<Brands> brands) {

        OtherBrands_Adaptar  otherBrands_adaptar= new OtherBrands_Adaptar(brands,getActivity());
        nameRecyclerView.setAdapter(otherBrands_adaptar);
        gridLayoutManager_OtherBrands = new GridLayoutManager(
                getActivity(), 4, GridLayoutManager.VERTICAL, false);

        nameRecyclerView.setLayoutManager(gridLayoutManager_OtherBrands);
        nameRecyclerView.setNestedScrollingEnabled(true);
        otherBrands_adaptar.notifyDataSetChanged();

        otherBrands_adaptar.setOnItemClickListener(new OtherBrands_Adaptar.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toasty.info(getActivity(),"Brand: "+brands.get(position).getBrandName()).show();
            }
        });

    }

    @Override
    public void setPopularBrands(List<Brands> brands) {

        Brands_HorizontalRecyclerViewAdapter brands_horizontalRecyclerViewAdapter = new Brands_HorizontalRecyclerViewAdapter(brands,getActivity());
        imageRecyclerView.setAdapter(brands_horizontalRecyclerViewAdapter);
        gridLayoutManager_PopularBrands = new GridLayoutManager(
                getActivity(), 3, GridLayoutManager.VERTICAL, false);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        imageRecyclerView.setLayoutManager(gridLayoutManager_PopularBrands);
        imageRecyclerView.setNestedScrollingEnabled(true);
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

    private void setToolbar(){;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }
}
