package com.fwts.cityfreshapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fwts.cityfreshapp.R;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHold> {
    Context context;
    ArrayList<Order> list;

    public AdminAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adminorder,parent,false);
        return new ViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Order order = list.get(position);
        holder.orderDate.setText(order.getDate());
        holder.orderUser.setText(order.getName());
        holder.orderPhone.setText(order.getPhone());
        holder.orderAddress.setText(order.getAddress());
        holder.orderProduct.setText(order.getProductName());
        holder.orderQty.setText(order.getProductQty());
        holder.orderPrice.setText(order.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHold extends RecyclerView.ViewHolder{

        TextView orderDate,orderUser,orderPhone,orderAddress,
                orderProduct,orderQty,orderPrice;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.dateOfOrder);
            orderUser = itemView.findViewById(R.id.username);
            orderPhone = itemView.findViewById(R.id.userphone);
            orderAddress = itemView.findViewById(R.id.useraddress);
            orderProduct = itemView.findViewById(R.id.productNameOrder);
            orderQty = itemView.findViewById(R.id.productQtyOrder);
            orderPrice = itemView.findViewById(R.id.productPriceOrder);




        }
    }
}
