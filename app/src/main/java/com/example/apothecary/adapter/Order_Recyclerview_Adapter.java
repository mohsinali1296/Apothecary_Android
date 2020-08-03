package com.example.apothecary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.apothecary.R;
import com.example.apothecary.models.user_order.Datum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Order_Recyclerview_Adapter extends RecyclerView.Adapter<Order_Recyclerview_Adapter.RecyclerViewHolder>
        implements Filterable {

    private static RecyclerViewHolder myViewHolder;
    static ClickListener clickListener;
    Context context;
    private List<Datum> orders;
    private List<Datum> ordersListFull;

    public Order_Recyclerview_Adapter(Context context, List<Datum> orders) {
        this.context = context;
        this.orders = orders;
        ordersListFull = orders;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.order_recyclerview_layout,
                viewGroup, false);
        myViewHolder = new RecyclerViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {

        if(orders!=null){
            viewHolder.OrderNo.setText("Order# URO-"+Integer.toString(orders.get(position).getOrderId()));
            viewHolder.ItemCount.setText(Integer.toString(orders.get(position).getTotalItem()));
            viewHolder.TotalAmount.setText(Double.toString(orders.get(position).getTotalAmount()));
            viewHolder.Date.setText(orders.get(position).getDate());
            viewHolder.Time.setText(orders.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.orderNo)
        TextView OrderNo;
        @BindView(R.id.itemCount)
        TextView ItemCount;
        @BindView(R.id.totalAmount)
        TextView TotalAmount;
        @BindView(R.id.Date)
        TextView Date;
        @BindView(R.id.Time)
        TextView Time;
        @BindView(R.id.detailBtn)
        TextView detailBtn;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        int position  = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            clickListener.onDetailClick(getAdapterPosition());
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
            clickListener.onDetailClick(getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ClickListener listener) {
        clickListener = listener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onDetailClick(int position);
    }

    @Override
    public Filter getFilter() {
        return orderFilter;
    }

    private Filter orderFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Datum> filteredList = new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filteredList.addAll(ordersListFull);
            }else{
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for(Datum item : ordersListFull){
                    if(item.getDate().toLowerCase().contains(filteredPattern)

                          //  Integer.toString(item.getOrderId()).toLowerCase().contains(filteredPattern)
                    ){
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
            orders.clear();
            orders.addAll((Collection<? extends Datum>) results.values);
            notifyDataSetChanged();
        }
    };

}
