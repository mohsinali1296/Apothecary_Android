package com.example.apothecary.adapter;

import android.os.Bundle;

import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.listdata.ListData;
import com.example.apothecary.views.view.Category.CategoriesFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerCategoryAdapter extends FragmentPagerAdapter {

    private List<ListData> categories;
    int Tab_Position;

    public ViewPagerCategoryAdapter(@NonNull FragmentManager fm,List<ListData> categories, int tabPosition) {
        super(fm);
        this.categories = categories;
        this.Tab_Position = tabPosition;

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("EXTRA_DATA_TABPOSITION",Tab_Position);
        bundle.putInt("EXTRA_DATA_ID",categories.get(position).getId());
        bundle.putString("EXTRA_DATA_NAME",categories.get(position).getDataName());
        bundle.putString("EXTRA_DATA_DESC",categories.get(position).getDescription() );
        bundle.putString("EXTRA_DATA_IMAGE",getImageUrl(categories.get(position).getImage()));
        fragment.setArguments(bundle);
        return fragment;
    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    @Override
    public int getCount() { return categories.size(); }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getDataName();
    }
}
