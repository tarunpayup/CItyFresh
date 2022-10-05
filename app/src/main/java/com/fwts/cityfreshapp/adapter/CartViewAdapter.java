package com.fwts.cityfreshapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fwts.cityfreshapp.R;
import com.fwts.cityfreshapp.ShowProductQuantity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.MyViewHolder> {

    public int oneProductTotalPrice;

    Context context;
    ArrayList<CartAdapter> list;
    DatabaseReference databaseReference;
    String value;

    public CartViewAdapter(Context context, ArrayList<CartAdapter> list) {
        this.context = context;
        this.list = list;
        value = Paper.book().read("phone");
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(value);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartAdapter cartAdapter = list.get(position);
        holder.productName.setText(cartAdapter.getProductName());
        holder.totalPrice.setText(cartAdapter.getTotalPrice());
        holder.productQty.setText(cartAdapter.getProductQty());
        String valuef = String.valueOf(position);
        int valuf = position;
        String productFirebaseImage = cartAdapter.getProductImage();
        Glide.with(context).load(productFirebaseImage).into(holder.productImageUnit);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(list.get(holder.getAdapterPosition()).getKey())
                        .removeValue();

                notifyDataSetChanged();

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, ShowProductQuantity.class)
                        .putExtra("cart_product", cartAdapter));

            }
        });

/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.editcartoption);
                TextView closeBtn = dialog.findViewById(R.id.closebtnedit);
                Button editCart = dialog.findViewById(R.id.editcart);
                Button deleteCart = dialog.findViewById(R.id.deleteitems);
                dialog.setCanceledOnTouchOutside(false);

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                editCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Toast.makeText(context, idval, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productQty, totalPrice;
        ImageView productImageUnit;
        Button edit, delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameDisplay);
            productQty = itemView.findViewById(R.id.productQtyDisplay);
            totalPrice = itemView.findViewById(R.id.productPriceDisplay);
            productImageUnit = itemView.findViewById(R.id.cartproductimage);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
}
