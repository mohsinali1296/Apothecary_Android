package com.example.apothecary.adapter;

import android.content.Context;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.articles.Image;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class HomeViewPagerBrandsAdapter extends PagerAdapter {

    private List<Brands> brands;
    private Context context;
    private static ClickListener clickListener;

    public HomeViewPagerBrandsAdapter(List<Brands> brands, Context context) {
        this.brands = brands;
        this.context = context;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        HomeViewPagerBrandsAdapter.clickListener = clickListener;
    }

    @Override
    public int getCount() { return brands.size(); }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { return view.equals(object); }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.brands_view_pager,
                container,false
        );

        ImageView brandsThumb = view.findViewById(R.id.brandsThumb);
        String brandsThumb_url = null;
        if(brands.get(position).getImage()!=null){
            brandsThumb_url = this.getImageUrl(brands.get(position).getImage());
            Picasso.get().load(brandsThumb_url).into(brandsThumb);
        }


        view.setOnClickListener(v-> clickListener.onClick(v, position));
        container.addView(view, 0);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }

    private String getURL(String url){
        String  URL = "";
        for (int i=0;i<url.length();i++){
            if(url.charAt(i)==92)//ASCII Value of '\'= 92
            {i++;}
            URL += url.charAt(i);
        }
        return URL;
    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;

    }
}
