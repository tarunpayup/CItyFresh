package com.fwts.cityfreshapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fwts.cityfreshapp.Model.PreviousOrdersModel;
import com.fwts.cityfreshapp.R;

import java.util.ArrayList;

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.ThePreviousAdapter>{

    Context context;
    ArrayList<PreviousOrdersModel> order;

    public PreviousAdapter(Context context, ArrayList<PreviousOrdersModel> order) {
        this.context = context;
        this.order = order;
    }

    @NonNull
    @Override
    public ThePreviousAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.previousproductitem,parent,false);
        return new ThePreviousAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThePreviousAdapter holder, int position) {
        PreviousOrdersModel previousOrdersModel = order.get(position);
        holder.productNameDisplaypp.setText(previousOrdersModel.getPpName());
        holder.productPriceDisplaypp.setText(previousOrdersModel.getPpPrice());
        holder.productQtyDisplaypp.setText(previousOrdersModel.getPpQty());
        holder.dateandtimepp.setText(previousOrdersModel.getDateTime());

    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public static class ThePreviousAdapter extends RecyclerView.ViewHolder{
        TextView productNameDisplaypp,productPriceDisplaypp,productQtyDisplaypp
                ,productUnitDisplaypp,dateandtimepp;

        public ThePreviousAdapter(@NonNull View itemView) {
            super(itemView);

            productNameDisplaypp = itemView.findViewById(R.id.productNameDisplaypp);
            productPriceDisplaypp = itemView.findViewById(R.id.productPriceDisplaypp);
            productQtyDisplaypp = itemView.findViewById(R.id.productQtyDisplaypp);
            productUnitDisplaypp = itemView.findViewById(R.id.productUnitDisplaypp);
            dateandtimepp = itemView.findViewById(R.id.dateandtimepp);
        }
    }
}
