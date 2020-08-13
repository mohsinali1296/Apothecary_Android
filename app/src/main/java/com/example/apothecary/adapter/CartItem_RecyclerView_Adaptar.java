package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.cart.AddCart;
import com.example.apothecary.models.cart.Datum;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItem_RecyclerView_Adaptar extends  RecyclerView.Adapter<CartItem_RecyclerView_Adaptar.RecyclerViewHolder>{

   public static ClickListener listener;
    List<Datum> cartList;
    Context context;
    private static RecyclerViewHolder myViewHolder;

    public CartItem_RecyclerView_Adaptar(List<Datum> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.cart_item_recyclerview,
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


        if(cartList!=null) {
           // String strProductId = products.get(position).getProductId().toString();
            viewHolder.ProductName.setText(cartList.get(position).getProductName().toString());
            viewHolder.ProductDesc.setText(cartList.get(position).getItemDescription().toString());

            viewHolder.Qty.setText(cartList.get(position).getQuantity().toString());
            viewHolder.Pharmacy.setText(cartList.get(position).getPharmacyName().toString());
            viewHolder.CartType.setText(Integer.toString(cartList.get(position).getCartType()));
            viewHolder.PrescriptionText.setText(cartList.get(position).getPrecriptionRequired_Text());

            if(cartList.get(position).getCategoryId()!=48){
                viewHolder.Price.setText(cartList.get(position).getUnitPrice().toString()+"/-");
                viewHolder.TotalPrice.setText(cartList.get(position).getTotalPrice().toString());
            }else{

                double total=0;
                switch (cartList.get(position).getCartType()){
                    case 0:
                        viewHolder.Qty_Type.setText("Per Unit Price");
                        viewHolder.Price.setText(cartList.get(position).getUnitPrice().toString());
                         total = cartList.get(position).getUnitPrice() * cartList.get(position).getQuantity();
                        viewHolder.TotalPrice.setText(Double.toString(total));
                        break;

                    case 1:
                        viewHolder.Qty_Type.setText("Per Leaf Price");
                        viewHolder.Price.setText(cartList.get(position).getLeafPrice().toString());
                        total = cartList.get(position).getLeafPrice() * cartList.get(position).getQuantity();
                        viewHolder.TotalPrice.setText(Double.toString(total));
                        break;

                    case 2:
                        viewHolder.Qty_Type.setText("Per Box Price");
                        viewHolder.Price.setText(cartList.get(position).getBoxPrice().toString());
                         total = cartList.get(position).getBoxPrice() * cartList.get(position).getQuantity();
                        viewHolder.TotalPrice.setText(Double.toString(total));
                        break;

                }

            }

          /*  switch (cartList.get(position).getCartType()){
                case 0:
                    viewHolder.Qty_Type.setText("Per Unit Price");
                    break;

                case 1:
                    viewHolder.Qty_Type.setText("Per Leaf Price");
                    break;

                case 2:
                    viewHolder.Qty_Type.setText("Per Box Price");
                    break;
            }*/

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
        return cartList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.cart_ProductImage)
        ImageView ProductImage;
        @BindView(R.id.Cart_AddQty_Button)
        ImageView Add_Btn;
        @BindView(R.id.Cart_SubQty_Button)
        ImageView Sub_Btn;
       // @BindView(R.id.Cart_Delete_Btn)
       // ImageView DeleteBtn;
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
        @BindView(R.id.cart_CartType)
        TextView CartType;
       // @BindView(R.id.Cart_RemoveButton)
       // Button RemoveButton;
        @BindView(R.id.Cart_Qty_EditText)
        EditText Qty;
        @BindView(R.id.RemoveContainer)
        ConstraintLayout Remove_Btn;
        @BindView(R.id.Cart_MaterialCheckBox)
        MaterialCheckBox item_CheckBox;
        @BindView(R.id.PresciptionText)
        TextView PrescriptionText;



        RecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
           // deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onClick(v,getAdapterPosition());

                        }
                    }
                }
            });

            Add_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onAddBtn_Click(getAdapterPosition());
                        }
                    }
                }
            });

            Sub_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){

                            listener.onSubBtn_Click(getAdapterPosition());
                        }
                    }
                }
            });



          /*  DeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onRemoveBtn_Click(v,getAdapterPosition());
                        }
                    }
                }
            });*/

            Remove_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onRemoveBtn_Click(getAdapterPosition());
                        }
                    }
                }
            });

            item_CheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            if(item_CheckBox.isChecked()){
                                listener.onAddCheckBox_Click(getAdapterPosition());
                            }else if(!item_CheckBox.isChecked()){
                                listener.onRemoveCheckBox_Click(getAdapterPosition());
                            }

                        }
                    }
                }
            });


        }



        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
            listener.onAddBtn_Click(getAdapterPosition());
            listener.onRemoveBtn_Click(getAdapterPosition());
            listener.onSubBtn_Click(getAdapterPosition());
            listener.onAddCheckBox_Click(getAdapterPosition());
            listener.onRemoveCheckBox_Click(getAdapterPosition());
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CartItem_RecyclerView_Adaptar.listener = clickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);
        void onAddBtn_Click(int position);
        void onSubBtn_Click(int position);
        void onRemoveBtn_Click( int position);
        void onAddCheckBox_Click( int position);
        void onRemoveCheckBox_Click( int position);

    }

}
