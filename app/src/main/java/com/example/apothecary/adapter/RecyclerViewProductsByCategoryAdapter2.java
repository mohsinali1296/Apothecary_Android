package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.pharmacies.Datum;
import com.example.apothecary.models.product.Products;
import com.example.apothecary.models.stock.Stock;
import com.example.apothecary.models.stock.Stocks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class RecyclerViewProductsByCategoryAdapter2
        extends  RecyclerView.Adapter<RecyclerViewProductsByCategoryAdapter2.RecyclerViewHolder>

{
    private List<Stocks> products;
    private Context context;
    private static ClickListener clickListener;
    private static RecyclerViewHolder myViewHolder;


    public RecyclerViewProductsByCategoryAdapter2(Context context, List<Stocks> products) {
        this.products = products;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
         View  view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_products,
                viewGroup, false);
     myViewHolder = new RecyclerViewHolder(view);

        return  myViewHolder;
    }

    public int getViewHolderPosition(){
        return myViewHolder.getAdapterPosition();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        if(products!=null) {
            String strProductId = products.get(position).getProductId().toString();
            viewHolder.productId.setText(strProductId);

            String strProductName = products.get(position).getProductName();
            viewHolder.productName.setText(strProductName);

            String strProductDesc = products.get(position).getItemDescription();
            viewHolder.productDesc.setText(strProductDesc);

            String strPharmacy = products.get(position).getPharmacyName();
            viewHolder.pharmacyName.setText(strPharmacy);

            if (products.get(position).getAvailable() == 0) {
                viewHolder.available.setText("No");
            } else if (products.get(position).getAvailable() == 1) {
                viewHolder.available.setText("Yes");
            }


            if(products.get(position).getCategoryId()==48){
                viewHolder.priceLabel.setText("Price per Tablet(Rs.):");
                String strProductPrice = products.get(position).getUnitPrice().toString();
                viewHolder.productPrice.setText(strProductPrice);
            }
            else{
                if(products.get(position).getUnitPrice()!=0
                        && products.get(position).getBoxPrice()==0){
                    viewHolder.priceLabel.setText("Unit Price(Rs.):");
                    String strProductPrice = products.get(position).getUnitPrice().toString();
                    viewHolder.productPrice.setText(strProductPrice);
                }else if(products.get(position).getUnitPrice()==0
                        && products.get(position).getBoxPrice()!=0){
                    viewHolder.priceLabel.setText("Pack Price(Rs.):");
                    String strProductPrice = products.get(position).getBoxPrice().toString();
                    viewHolder.productPrice.setText(strProductPrice);
                }
            }

            String strProductThumb_url = null;
            if (products.get(position).getImage() != null) {
                strProductThumb_url = getImageUrl(products.get(position).getImage());
                try {
                    Picasso.get().load(strProductThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.productThumb);
                } catch (Exception ex) {

                }
            }


        }

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

    @Override
    public int getItemCount() {
        return this.products.size();
    }


    public   static class RecyclerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        @BindView(R.id.productId2)
        TextView productId;
        @BindView(R.id.ProductList_RelativeLayout)
        RelativeLayout relativeLayoutProduct;
        @BindView(R.id.productImage2)
        ImageView productThumb;
        @BindView(R.id.productName2)
        TextView productName;
        @BindView(R.id.productDesc2)
        TextView productDesc;
        @BindView(R.id.productPrice2)
        TextView productPrice;
        @BindView(R.id.availableText2)
        TextView available;
        @BindView(R.id.pharmacyName2)
        TextView pharmacyName;
        @BindView(R.id.favouriteToggleButton)
        ToggleButton favouriteButton;
        @BindView(R.id.priceLabel)
        TextView priceLabel;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        int position  = getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION){
                            if(favouriteButton.isChecked()){
                               // clickListener.AddItem(v,position);
                            }
                            if(favouriteButton.isChecked()){
                                //clickListener.RemoveItem(v,position);
                            }
                            //clickListener.onItemClick(v,position);
                        }
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
            //clickListener.onClick(myViewHolder, getAdapterPosition());
        }
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewProductsByCategoryAdapter2.clickListener = clickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);

    }


}
