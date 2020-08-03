package com.example.apothecary.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.pharmacies.Datum;
import com.example.apothecary.models.product.Products;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Pharmacy_Adapter extends RecyclerView.Adapter<Pharmacy_Adapter.RecyclerViewHolder>
implements Filterable {

    private static RecyclerViewHolder myViewHolder;
    Context context;
    private List<Datum> pharmacy;
    private List<Datum> pharmacyListFull;
    static ClickListener clickListener;

    public Pharmacy_Adapter(Context context, List<Datum> pharmacy) {
        this.context = context;
        this.pharmacy = pharmacy;
        pharmacyListFull= new ArrayList<>(pharmacy);
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.pharmacylist_recyclerview,
                viewGroup, false);
        myViewHolder = new RecyclerViewHolder(view);

        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        if(pharmacy!=null){
            viewHolder.pharm_Id.setText(pharmacy.get(position).getId().toString());
            viewHolder.pharm_Name.setText(pharmacy.get(position).getPharmName().toString());
            viewHolder.pharm_Address.setText(pharmacy.get(position).getPharmacyAddress().toString());
            viewHolder.pharm_Email.setText(pharmacy.get(position).getEmail().toString());
            viewHolder.pharm_Contact.setText(pharmacy.get(position).getContact().toString());

            double dis = Math.round(pharmacy.get(position).getPharmacyDistance()*100.0)/100.0;
            viewHolder.pharm_Distance.setText(Double.toString(dis)+" kms away");

            if(pharmacy.get(position).getAverage_Rating()!=null){

                double avgRating = Math.round(Double.valueOf(pharmacy.get(position).getAverage_Rating())*1000.0)/1000.0;
                viewHolder.pharm_Rating.setText(Double.toString(avgRating)+"/5");
                viewHolder.ratingBar.setRating((float)avgRating);




            }else{
                viewHolder.pharm_Rating.setText("Not Rated yet");
                viewHolder.ratingBar.setRating(0);
                viewHolder.ratingBar.setVisibility(View.GONE);
            }


            String strProductThumb_url = null;
            if (pharmacy.get(position).getImage() != null) {
                strProductThumb_url = getImageUrl(pharmacy.get(position).getImage());
                try {
                    Picasso.get().load(strProductThumb_url).placeholder(R.drawable.ic_circle2).into(viewHolder.pharm_Image);
                } catch (Exception ex) {

                }
            }

        }

    }



    private String getImageUrl(String imagePath){
        String url=null;
        ApothecaryClient api = new ApothecaryClient();
        url = api.getBaseUrl()+"uploads/images/"+imagePath;
        return  url;
    }

    @Override
    public int getItemCount() {
        return pharmacy.size();
    }



    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.pharmacy_PharmacyId)
        TextView pharm_Id;
        @BindView(R.id.pharmacy_PharmacyName)
        TextView pharm_Name;
        @BindView(R.id.pharmacy_PharmacyAddress)
        TextView pharm_Address;
        @BindView(R.id.pharmacy_PharmacyContact)
        TextView pharm_Contact;
        @BindView(R.id.pharmacy_PharmacyEmail)
        TextView pharm_Email;
        @BindView(R.id.pharmacy_Rating)
        TextView pharm_Rating;
        @BindView(R.id.pharmacy_Distance)
        TextView pharm_Distance;
        @BindView(R.id.pharmacy_PharmacyImage)
        ImageView pharm_Image;
        @BindView(R.id.pharmacy_Ratingbar)
        RatingBar ratingBar;
     /*   @BindView(R.id.pharmacy_star1)
        ImageView star1;
        @BindView(R.id.pharmacy_star2)
        ImageView star2;
        @BindView(R.id.pharmacy_star3)
        ImageView star3;
        @BindView(R.id.pharmacy_star4)
        ImageView star4;
        @BindView(R.id.pharmacy_star5)
        ImageView star5;*/


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
        Pharmacy_Adapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    @Override
    public Filter getFilter() {
        return pharmacyFilter;
    }

    private Filter pharmacyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Datum> filteredList = new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filteredList.addAll(pharmacyListFull);
            }else{
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for(Datum item : pharmacyListFull){
                        if(item.getPharmName().toLowerCase().contains(filteredPattern)){
                            filteredList.add(item);
                        }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                 pharmacy.clear();
                 pharmacy.addAll((Collection<? extends Datum>) results.values);
                 notifyDataSetChanged();
        }
    };

}
