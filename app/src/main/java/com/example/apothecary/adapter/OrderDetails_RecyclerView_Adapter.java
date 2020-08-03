package com.example.apothecary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.userorderdetails.OrderDetail;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetails_RecyclerView_Adapter extends
        RecyclerView.Adapter<OrderDetails_RecyclerView_Adapter.RecyclerViewHolder>
{

    private List<OrderDetail> orderDetails;
    private Context context;

    public OrderDetails_RecyclerView_Adapter(Context context,List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderdetails_recyclerview,
                viewGroup, false);
        return new OrderDetails_RecyclerView_Adapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        viewHolder.ProductName.setText(orderDetails.get(position).getProductName());
        viewHolder.ProductDesc.setText(orderDetails.get(position).getItemDescription());
        viewHolder.TotalPrice.setText(Double.toString(orderDetails.get(position).getTotalPrice()));
        viewHolder.TotalQty.setText(Integer.toString(orderDetails.get(position).getItemOrderedQuantity()));
        viewHolder.PharmacyName.setText(orderDetails.get(position).getPharmacyName());
        viewHolder.Status.setText(orderDetails.get(position).getOrderStatus());

        switch (orderDetails.get(position).getStockType()){

            case 0:
                if(orderDetails.get(position).getCategoryName()=="Medicine"){
                    viewHolder.Product_QtyType.setText("Quantity per Unit Tablet");
                }
                break;

            case 1:
                viewHolder.Product_QtyType.setText("Quantity per Leaf");
                break;

            case  2:
                viewHolder.Product_QtyType.setText("Quantity per Box");
                break;

        }

        String strProductThumb_url = null;
        if (orderDetails.get(position).getImage() != null) {
            strProductThumb_url = getImageUrl(orderDetails.get(position).getImage());
            try {
                Picasso.get().load(strProductThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.ProductImage);
            } catch (Exception ex) {

            }
        }

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.productImage)
        ImageView ProductImage;
        @BindView(R.id.productName)
        TextView ProductName;
        @BindView(R.id.productDesc)
        TextView ProductDesc;
        @BindView(R.id.TotalPrice)
        TextView TotalPrice;
        @BindView(R.id.TotalQuantity)
        TextView TotalQty;
        @BindView(R.id.Product_QtyType)
        TextView Product_QtyType;
        @BindView(R.id.PharmacyName)
        TextView PharmacyName;
        @BindView(R.id.Status)
        TextView Status;



        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
