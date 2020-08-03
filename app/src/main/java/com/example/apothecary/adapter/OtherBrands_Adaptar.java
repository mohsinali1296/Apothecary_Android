package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.models.brands.Brands;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherBrands_Adaptar extends RecyclerView.Adapter<OtherBrands_Adaptar.RecyclerViewHolder> {

    private List<Brands> brands;
    private Context context;
    private static ClickListener clickListener;

    public OtherBrands_Adaptar(List<Brands> brands, Context context) {
        this.brands = brands;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brands_name_recyclerview,
                viewGroup, false);
        return new OtherBrands_Adaptar.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        viewHolder.brandsName.setText(brands.get(position).getBrandName());
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.others_BrandsName)
        TextView brandsName;

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
        OtherBrands_Adaptar.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

}
