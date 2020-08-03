package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.listdata.ListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class HomeViewPagerHeaderAdapter extends PagerAdapter {

    private List<ListData> newsFeed;
    private Context context;
    private static ClickListener clickListener;

    public HomeViewPagerHeaderAdapter(List<ListData> newsFeed, Context context) {
        this.newsFeed = newsFeed;
        this.context=context;
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        HomeViewPagerHeaderAdapter.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return newsFeed.size();
        //return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) { return view.equals(o); }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_view_pager_header,
                container,
                false
        );

        ImageView newsFeedThumb = view.findViewById(R.id.newsFeedThumb);
        String newsFeedThumb_url=null;
        if(newsFeed.get(position).getImage()!=null){
            newsFeedThumb_url = this.getImageUrl(newsFeed.get(position).getImage());
            //newsFeedThumb_url = newsFeed.get(position).getImageUrl();
            try{
                Picasso.get().load(newsFeedThumb_url).into(newsFeedThumb);

            }catch(Exception ex){
                newsFeedThumb_url=getURL(newsFeedThumb_url);
                Picasso.get().load(newsFeedThumb_url).into(newsFeedThumb);
            }
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
