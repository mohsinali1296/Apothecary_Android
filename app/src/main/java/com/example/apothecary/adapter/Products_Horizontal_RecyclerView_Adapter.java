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
import com.example.apothecary.models.stock.Stocks;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Products_Horizontal_RecyclerView_Adapter extends
        RecyclerView.Adapter<Products_Horizontal_RecyclerView_Adapter.RecyclerViewHolder> {

    private List<Stocks> stocks;
    private Context context;
    private static ClickListener clickListener;

    public Products_Horizontal_RecyclerView_Adapter(List<Stocks> stocks, Context context) {
        this.stocks = stocks;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_products,
                viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        viewHolder.productName.setText(stocks.get(position).getProductName());
        viewHolder.productDesc.setText(stocks.get(position).getItemDescription());
        viewHolder.productPrice.setText("Rs. "+stocks.get(position).getUnitPrice().toString());

        String productThumb_url=null;
        if(stocks.get(position).getImage()!=null){
            productThumb_url = getImageUrl(stocks.get(position).getImage());
            try{
                Picasso.get().load(productThumb_url).placeholder(R.drawable.ic_circle2).into(viewHolder.productThumb);
            }catch (Exception ex){
                productThumb_url=getURL(productThumb_url);
                Picasso.get().load(productThumb_url).placeholder(R.drawable.ic_circle2).into(viewHolder.productThumb);
            }
        }

    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }


    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.horizontal_Product_Image)
        ImageView productThumb;
        @BindView(R.id.horizontal_ProductName)
        TextView productName;
        @BindView(R.id.horizontal_ProductDesc)
        TextView productDesc;
        @BindView(R.id.horizontal_ProductPrice)
        TextView productPrice;

        /// ClickListener itemClickListener;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { clickListener.onClick(v, getAdapterPosition()); }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Products_Horizontal_RecyclerView_Adapter.clickListener = clickListener;
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

    public interface ClickListener {
        void onClick(View view, int position);
    }

}
