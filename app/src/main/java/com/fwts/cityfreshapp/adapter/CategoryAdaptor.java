package com.fwts.cityfreshapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fwts.cityfreshapp.Model.ProductModel;
import com.fwts.cityfreshapp.R;

import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    Context context;
    ArrayList<ProductModel> dataModels = new ArrayList<>();
    OnItemCategory onItemCategory;

    public CategoryAdaptor(Context context, ArrayList<ProductModel> dataModels, OnItemCategory onItemCategory) {
        this.context = context;
        this.dataModels = dataModels;
        this.onItemCategory = onItemCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductModel productModel = dataModels.get(position);
        holder.productNameDisplay.setText(productModel.getP_name());
        holder.productPriceDisplay.setText(productModel.getP_price() + "");
        holder.productQtyDisplay.setText(productModel.getP_qty() + "");
        holder.productUnitDisplay.setText(productModel.getP_unit());
        Glide.with(context).load(productModel.getImage()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemCategory != null) {
                    onItemCategory.onCategory(productModel);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public interface OnItemCategory {
        void onCategory(ProductModel productModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView productImage;
        TextView productUnitDisplay, productQtyDisplay, productNameDisplay, productPriceDisplay;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            productImage = view.findViewById(R.id.productImage);
            productNameDisplay = view.findViewById(R.id.productNameDisplay);
            productUnitDisplay = view.findViewById(R.id.productUnitDisplay);
            productQtyDisplay = view.findViewById(R.id.productQtyDisplay);
            productPriceDisplay = view.findViewById(R.id.productPriceDisplay);

        }
    }

}
