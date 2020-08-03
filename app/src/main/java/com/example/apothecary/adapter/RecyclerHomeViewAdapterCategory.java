package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.Utils;
import com.example.apothecary.api.ApothecaryApi;
import com.example.apothecary.api.ApothecaryClient;
import com.example.apothecary.models.listdata.ListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerHomeViewAdapterCategory
        extends RecyclerView.Adapter<RecyclerHomeViewAdapterCategory.RecyclerViewHolder>

{

    private List<ListData> categories;
    private Context context;
    private static ClickListener clickListener;

   public RecyclerHomeViewAdapterCategory(List<ListData> categories, Context context) {
       this.categories = categories;
        this.context = context;
       //this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public RecyclerHomeViewAdapterCategory.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_category,
                viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHomeViewAdapterCategory.RecyclerViewHolder viewHolder, int i) {

        String strCategoryName = categories.get(i).getDataName();
        viewHolder.categoryName.setText(strCategoryName);
        String strCategoryThumb_url=null;
        if(categories.get(i).getImage()!=null){
            strCategoryThumb_url = getImageUrl(categories.get(i).getImage());
            try{
                Picasso.get().load(strCategoryThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.categoryThumb);
            }catch (Exception ex){
                strCategoryThumb_url=getURL(strCategoryThumb_url);
                Picasso.get().load(strCategoryThumb_url).placeholder(R.drawable.ic_circle).into(viewHolder.categoryThumb);
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
        return categories.size();
    }



    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recyclerView_categoryThumb)
        ImageView categoryThumb;
        @BindView(R.id.recyclerView_categoryName)
        TextView categoryName;
      //  @BindView(R.id.category_cardview)
      //  CardView cardItem;
       /// ClickListener itemClickListener;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
          //  this.itemClickListener=itemClickListener;
            itemView.setOnClickListener(this);
            //cardItem.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerHomeViewAdapterCategory.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }


}
