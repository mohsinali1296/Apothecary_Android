package com.example.apothecary.views.view.products;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.stock.Stocks;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Bottomsheet_AddtoCart extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {

    private BottomSheetListener listener;
    Context context;

    @BindView(R.id.bottomsheet_AddtoCart_Image)
    ImageView ProductImage;
    @BindView(R.id.Cart_ProductName)
    TextView ProductName;
    @BindView(R.id.Cart_Desc)
    TextView ProductDesc;
    @BindView(R.id.bottomsheet_AddtoCart_Price)
    TextView ProductPrice;
    @BindView(R.id.bottomsheet_AddtoCart_Piece)
    TextView Piece;
    @BindView(R.id.Cart_RemoveButton)
    Button AddtoCart_Btn;
    @BindView(R.id.Cart_Qty_EditText)
    EditText Qty;
    @BindView(R.id.Cart_AddQty_Button)
    ImageView AddQty;
    @BindView(R.id.Cart_SubQty_Button)
    ImageView SubQty;
    @BindView(R.id.spinner_RelativeLayout)
    RelativeLayout spinnerLayout;
    @BindView(R.id.addtocart_Type_Spinner)
    Spinner type_Spinner;

    private static Stocks stocks;
    private static int carttype=0;

    String[] type = new String[]{
            "Tablets",
            "Leaf",
            "Box"
    };



    public Bottomsheet_AddtoCart(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_addtocart_layout,container,false);
        ButterKnife.bind(this,view);
        setProductData();
        ButtonEvents();
        SetSpinner();
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //Toast.makeText(context,"type 0",Toast.LENGTH_SHORT).show();
                if(stocks.getCategoryId()==48){
                    carttype=0;
                    ProductPrice.setText(Double.toString(stocks.getUnitPrice()));
                    Piece.setText(" (per Unit Tablet)");
                }else{
                    if(stocks.getUnitPrice()!=0){
                        carttype=0;
                        ProductPrice.setText(Double.toString(stocks.getUnitPrice()));
                        Piece.setText(" (per Unit Price)");
                    }else if(stocks.getUnitPrice()==0){
                        Toast.makeText(context,"No Price available",Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case 1:
                //Toast.makeText(context,"type 1",Toast.LENGTH_SHORT).show();
                if(stocks.getCategoryId()==48){
                    carttype=1;
                    ProductPrice.setText(Double.toString(stocks.getLeafPrice()));
                    Piece.setText(" (per Unit Leaf)");
                }else{
                    Toast.makeText(context,"No Price available",Toast.LENGTH_SHORT).show();
                }

                break;

            case 2:
                //Toast.makeText(context,"type 2",Toast.LENGTH_SHORT).show();
                if(stocks.getCategoryId()==48){
                    carttype=2;
                    ProductPrice.setText(Double.toString(stocks.getBoxPrice()));
                    Piece.setText(" (per Unit Pack)");
                }else{
                    if(stocks.getBoxPrice()!=0){
                        carttype=2;
                        ProductPrice.setText(Double.toString(stocks.getBoxPrice()));
                        Piece.setText(" (per Unit Pack)");
                    }else if(stocks.getBoxPrice()==0) {
                        Toast.makeText(context,"No Price available",Toast.LENGTH_SHORT).show();
                    }

                }

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface BottomSheetListener{
        void onAddToCartCicked(int qty,int carttype);
    }

    public void setProduct(Stocks stocks){
        this.stocks=stocks;
    }

    private void setProductData(){
        Qty.setText("0");
        ProductName.setText(stocks.getProductName());
        ProductDesc.setText(stocks.getItemDescription());
        Picasso.get().load(getImageUrl(stocks.getImage())).placeholder(R.drawable.ic_circle2).into(ProductImage);
        if(stocks.getCategoryId()==48){
            spinnerLayout.setVisibility(View.VISIBLE);
            ProductPrice.setText(Double.toString(stocks.getUnitPrice()));
            Piece.setText(" (per Unit Price)");
        }else{
            spinnerLayout.setVisibility(View.VISIBLE);
            ProductPrice.setText(Double.toString(stocks.getUnitPrice()));
            Piece.setText(" (per Unit Tablet)");

        }
    }

    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    private void SetSpinner() {

        if(stocks.getCategoryId()==48){
            type[0]="Unit Piece";
            type[1]="Leaf";
            type[2]="Pack";
        }else{
            type[0]="Unit Piece";
            type[1]="Leaf(Unavailable)";
            type[2]="Pack";
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.spinner_layout ,type
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        type_Spinner.setAdapter(spinnerArrayAdapter);

        type_Spinner.setOnItemSelectedListener(this);

    }

    private void ButtonEvents() {
        AddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.valueOf(Qty.getText().toString());
                if(qty<10){
                    qty +=1;
                    Qty.setText(Integer.toString(qty));
                }else{
                    Toasty.normal(context,"Maximum 10 items can be Selected",Toast.LENGTH_SHORT).show();
                }

            }
        });

        SubQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.valueOf(Qty.getText().toString());
                if(qty!=0){
                    qty -=1;
                    Qty.setText(Integer.toString(qty));
                }

            }
        });

        AddtoCart_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.valueOf(Qty.getText().toString());
                if(qty<=0){
                    Toast.makeText(context,"Please Select Quantity.",Toast.LENGTH_LONG).show();
                }else{
                    listener.onAddToCartCicked(qty,carttype);
                    dismiss();
                }
            }
        });
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            if(context instanceof  Bottomsheet_AddtoCart.BottomSheetListener){
                listener = (BottomSheetListener) context;
            }
        }catch (Exception e){
            throw new RuntimeException(context.toString()+" must implement AddtoCart_BottomSheet_ClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}


