package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.favourite.Datum;
import com.example.apothecary.models.stock.Stocks;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Favourite_RecyclerView_Adaptar extends  RecyclerView.Adapter<Favourite_RecyclerView_Adaptar.RecyclerViewHolder> {

    public static ClickListener clickListener;
    private List<Datum> products;
    private Context context;
    private static RecyclerViewHolder myViewHolder;

    public Favourite_RecyclerView_Adaptar(List<Datum> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.favourite_item_recyclerview,
                viewGroup, false);
        myViewHolder = new RecyclerViewHolder(view);



/*
        myViewHolder.relativeLayoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toasty.info(context,"Item Click at position "+String.valueOf(myViewHolder.getAdapterPosition()),Toasty.LENGTH_LONG).show();

            }
        });
*/
        //return new RecyclerViewHolder(view);
        return  myViewHolder;
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

            String strProductPrice = products.get(position).getUnitPrice().toString();
            viewHolder.productPrice.setText(strProductPrice);

            String strProductThumb_url = null;
            if (products.get(position).getImage() != null) {
                strProductThumb_url = getImageUrl(products.get(position).getImage());
                try {
                    Picasso.get().load(strProductThumb_url).placeholder(R.drawable.ic_circle2).into(viewHolder.productThumb);
                } catch (Exception ex) {

                }
            }


        }

    }

    @Override
    public int getItemCount() {
        return products.size();
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

    public void setOnItemClickListener(ClickListener clickListener) {
        Favourite_RecyclerView_Adaptar.clickListener = clickListener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

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
       // @BindView(R.id.favourite_AddtoCart)
       // TextView addToCart;
        @BindView(R.id.favourite_Delete)
        ImageView deleteButton;


        RecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        int position  = getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION){
                            clickListener.onDelete(getAdapterPosition());
                        }
                    }
                }
            });


       /*     addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        int position  = getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION){
                            clickListener.onAddtoCart(v,getAdapterPosition());
                        }
                    }
                }
            });*/

        }



        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
            //clickListener.onDelete(getAdapterPosition());
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onDelete(int position);
    }

}
