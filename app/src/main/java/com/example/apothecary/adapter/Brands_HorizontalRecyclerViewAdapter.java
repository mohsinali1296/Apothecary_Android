package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.listdata.ListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Brands_HorizontalRecyclerViewAdapter extends
RecyclerView.Adapter<Brands_HorizontalRecyclerViewAdapter.RecyclerViewHolder>
{
    private List<Brands> brands;
    private Context context;
    private static ClickListener clickListener;

    public Brands_HorizontalRecyclerViewAdapter(List<Brands> brands, Context context) {
        this.brands = brands;
        this.context = context;
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

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brands_horizontal_recyclerview,
                viewGroup, false);
        return new Brands_HorizontalRecyclerViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        String brandsThumb_url=null;
        if(brands.get(position).getImage()!=null){
            brandsThumb_url = getImageUrl(brands.get(position).getImage());
            try{
                Picasso.get().load(brandsThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.brandsThumb);
            }catch (Exception ex){
                brandsThumb_url=getURL(brandsThumb_url);
                Picasso.get().load(brandsThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.brandsThumb);
            }
        }

    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.horizontal_ProductImage)
        ImageView brandsThumb;

        /// ClickListener itemClickListener;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Brands_HorizontalRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
