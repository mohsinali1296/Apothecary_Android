package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.cart.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmCart_Recyclerview_Adapter extends RecyclerView.Adapter<ConfirmCart_Recyclerview_Adapter.RecyclerViewHolder> {
    List<Datum> cartList;
    Context context;
    private static RecyclerViewHolder myViewHolder;

    public ConfirmCart_Recyclerview_Adapter(Context context,List<Datum> cartList) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.confirm_cartitem_recyclerview,
                viewGroup, false);
        myViewHolder = new RecyclerViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        if(cartList!=null) {
            // String strProductId = products.get(position).getProductId().toString();
            viewHolder.ProductName.setText(cartList.get(position).getProductName().toString());
            viewHolder.ProductDesc.setText(cartList.get(position).getItemDescription().toString());
            viewHolder.TotalPrice.setText(cartList.get(position).getTotalPrice().toString());
            viewHolder.Total_Qty.setText(cartList.get(position).getQuantity().toString());
            viewHolder.Pharmacy.setText(cartList.get(position).getPharmacyName().toString());
            viewHolder.PrescriptionText.setText(cartList.get(position).getPrecriptionRequired_Text());

            if(cartList.get(position).getCategoryId()!=48){
                viewHolder.Price.setText(cartList.get(position).getUnitPrice().toString()+"/-");
            }else{

                switch (cartList.get(position).getCartType()){
                    case 0:
                        viewHolder.Qty_Type.setText("Per Unit Price");
                        viewHolder.Price.setText(cartList.get(position).getUnitPrice().toString()+"/-");
                        break;

                    case 1:
                        viewHolder.Qty_Type.setText("Per Leaf Price");
                        viewHolder.Price.setText(cartList.get(position).getLeafPrice().toString()+"/-");
                        break;

                    case 2:
                        viewHolder.Qty_Type.setText("Per Box Price");
                        viewHolder.Price.setText(cartList.get(position).getBoxPrice().toString()+"/-");
                        break;

                }

            }

            String strProductThumb_url = null;
            if (cartList.get(position).getImage() != null) {
                strProductThumb_url = getImageUrl(cartList.get(position).getImage());
                try {
                    Picasso.get().load(strProductThumb_url).placeholder(R.drawable.ic_circle2).into(viewHolder.ProductImage);
                } catch (Exception ex) {

                }
            }


        }

    }

    @Override
    public int getItemCount() {
        return cartList.size();
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

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cart_ProductImage)
        ImageView ProductImage;
        @BindView(R.id.Cart_ProductName)
        TextView ProductName;
        @BindView(R.id.Cart_Desc)
        TextView ProductDesc;
        @BindView(R.id.Cart_TotalPrice)
        TextView TotalPrice;
        @BindView(R.id.Cart_Price)
        TextView Price;
        @BindView(R.id.Cart_PharmacyName)
        TextView Pharmacy;
        @BindView(R.id.Cart_QtyType)
        TextView Qty_Type;
        @BindView(R.id.Cart_TotalQuantity)
        TextView Total_Qty;
        @BindView(R.id.PresciptionText)
        TextView PrescriptionText;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
