package com.fwts.cityfreshapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fwts.cityfreshapp.Model.ProductModel;
import com.fwts.cityfreshapp.adapter.CategoryAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDisplayLayoutActivity extends AppCompatActivity {
    String type, work;
    ImageView cartactivity;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private RecyclerView displayProducts;
    private TextView tvProductCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display_layout);
        displayProducts = findViewById(R.id.displayProducts);
        tvProductCat = findViewById(R.id.tvProductCat);
        type = getIntent().getStringExtra("type");
        work = getIntent().getStringExtra("work");
        cartactivity = (ImageView) findViewById(R.id.cartactivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (work != null) {
            if (work.equals("editVegetable")) {
                Toast.makeText(this, "Edit Vegetables", Toast.LENGTH_SHORT).show();
                cartactivity.setVisibility(View.INVISIBLE);
                if (type != null) {
                    if (type.equals("Vegetables")) {
                        tvProductCat.setText("Edit Vegetables");
                        productModelArrayList.clear();
                        firebaseDatabase.getReference().child("Product").child("Vegetables").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (final DataSnapshot spShot : snapshot.getChildren()) {
                                    String key = spShot.getKey();
                                    String categoryType = (String) spShot.child("categoryType").getValue();
                                    String desc = (String) spShot.child("desc").getValue();
                                    String id = (String) spShot.child("id").getValue();
                                    String image = (String) spShot.child("image").getValue();
                                    String p_name = (String) spShot.child("p_name").getValue();
                                    String p_qty = (String) spShot.child("p_qty").getValue();
                                    String p_unit = (String) spShot.child("p_unit").getValue();
                                    String p_price = (String) spShot.child("p_price").getValue();
                                    if (categoryType.equals("Vegetables")) {
                                        productModelArrayList.add(new ProductModel(id, key, image, p_name, p_price, p_qty, p_unit, desc, categoryType));
                                        Log.e("product", "onDataChange: " + productModelArrayList.size());
                                    }
                                }
                                Log.e("size", "onDataChange:" + productModelArrayList.size());
                                CategoryAdaptor.OnItemCategory lis = new CategoryAdaptor.OnItemCategory() {
                                    @Override
                                    public void onCategory(ProductModel categoryModel) {
                                        //startActivity(new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class).putExtra("list", categoryModel));
                                        Intent intent = new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class);
                                        intent.putExtra("list", categoryModel);
                                        intent.putExtra("work", work);
                                        startActivity(intent);
                                        Toast.makeText(ProductDisplayLayoutActivity.this, "Edit Vegetable", Toast.LENGTH_SHORT).show();
                                    }
                                };
                                displayProducts.setAdapter(new CategoryAdaptor(ProductDisplayLayoutActivity.this, productModelArrayList, lis));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("error", "onCancelled:" + error.getMessage());

                            }
                        });
                    }
                }

            } else if (work.equals("editFruit")) {
                Toast.makeText(this, "Edit Fruits", Toast.LENGTH_SHORT).show();
                cartactivity.setVisibility(View.INVISIBLE);
                if (type != null) {
                    if (type.equals("Fruit")) {
                        tvProductCat.setText("Edit Fruits");
                        firebaseDatabase.getReference().child("Product").child("Fruits").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (final DataSnapshot spShot : snapshot.getChildren()) {
                                    String key = spShot.getKey();
                                    String categoryType = (String) spShot.child("categoryType").getValue();
                                    String desc = (String) spShot.child("desc").getValue();
                                    String id = (String) spShot.child("id").getValue();
                                    String image = (String) spShot.child("image").getValue();
                                    String p_name = (String) spShot.child("p_name").getValue();
                                    String p_qty = (String) spShot.child("p_qty").getValue();
                                    String p_unit = (String) spShot.child("p_unit").getValue();
                                    String p_price = (String) spShot.child("p_price").getValue();
                                    if (categoryType.equals("Fruits")) {
                                        productModelArrayList.add(new ProductModel(id, key, image, p_name, p_price, p_qty, p_unit, desc, categoryType));
                                        Log.e("product", "onDataChange: " + productModelArrayList.size());
                                    }
                                }
                                Log.e("size", "onDataChange:" + productModelArrayList.size());
                                CategoryAdaptor.OnItemCategory lis = new CategoryAdaptor.OnItemCategory() {
                                    @Override
                                    public void onCategory(ProductModel categoryModel) {
                                        //startActivity(new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class).putExtra("list", categoryModel));
                                        Intent intent = new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class);
                                        intent.putExtra("list", categoryModel);
                                        intent.putExtra("work", work);
                                        startActivity(intent);
                                        Toast.makeText(ProductDisplayLayoutActivity.this, "Edit Fruits", Toast.LENGTH_SHORT).show();

                                    }
                                };
                                displayProducts.setAdapter(new CategoryAdaptor(ProductDisplayLayoutActivity.this, productModelArrayList, lis));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("error", "onCancelled:" + error.getMessage());

                            }
                        });
                    }
                }

            }
        } else {
            cartactivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProductDisplayLayoutActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            });

            if (type != null) {
                if (type.equals("Vegetables")) {
                    tvProductCat.setText("Vegetables");
                    productModelArrayList.clear();
                    firebaseDatabase.getReference().child("Product").child("Vegetables").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot spShot : snapshot.getChildren()) {
                                String key = spShot.getKey();
                                String categoryType = (String) spShot.child("categoryType").getValue();
                                String desc = (String) spShot.child("desc").getValue();
                                String id = (String) spShot.child("id").getValue();
                                String image = (String) spShot.child("image").getValue();
                                String p_name = (String) spShot.child("p_name").getValue();
                                String p_qty = (String) spShot.child("p_qty").getValue();
                                String p_unit = (String) spShot.child("p_unit").getValue();
                                String p_price = (String) spShot.child("p_price").getValue();
                                if (categoryType.equals("Vegetables")) {
                                    productModelArrayList.add(new ProductModel(id, key, image, p_name, p_price, p_qty, p_unit, desc, categoryType));
                                    Log.e("product", "onDataChange: " + productModelArrayList.size());
                                }
                            }
                            Log.e("size", "onDataChange:" + productModelArrayList.size());
                            CategoryAdaptor.OnItemCategory lis = new CategoryAdaptor.OnItemCategory() {
                                @Override
                                public void onCategory(ProductModel categoryModel) {
                                    startActivity(new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class).putExtra("list", categoryModel).putExtra("work", work));
                                }
                            };
                            displayProducts.setAdapter(new CategoryAdaptor(ProductDisplayLayoutActivity.this, productModelArrayList, lis));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("error", "onCancelled:" + error.getMessage());

                        }
                    });
                } else if (type.equals("Fruit")) {
                    tvProductCat.setText("Fruits");
                    firebaseDatabase.getReference().child("Product").child("Fruits").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot spShot : snapshot.getChildren()) {
                                String key = spShot.getKey();
                                String categoryType = (String) spShot.child("categoryType").getValue();
                                String desc = (String) spShot.child("desc").getValue();
                                String id = (String) spShot.child("id").getValue();
                                String image = (String) spShot.child("image").getValue();
                                String p_name = (String) spShot.child("p_name").getValue();
                                String p_qty = (String) spShot.child("p_qty").getValue();
                                String p_unit = (String) spShot.child("p_unit").getValue();
                                String p_price = (String) spShot.child("p_price").getValue();
                                if (categoryType.equals("Fruits")) {
                                    productModelArrayList.add(new ProductModel(id, key, image, p_name, p_price, p_qty, p_unit, desc, categoryType));
                                    Log.e("product", "onDataChange: " + productModelArrayList.size());
                                }
                            }
                            Log.e("size", "onDataChange:" + productModelArrayList.size());
                            CategoryAdaptor.OnItemCategory lis = new CategoryAdaptor.OnItemCategory() {
                                @Override
                                public void onCategory(ProductModel categoryModel) {
                                    startActivity(new Intent(ProductDisplayLayoutActivity.this, ShowProductQuantity.class).putExtra("list", categoryModel).putExtra("work", work));
                                }
                            };
                            displayProducts.setAdapter(new CategoryAdaptor(ProductDisplayLayoutActivity.this, productModelArrayList, lis));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("error", "onCancelled:" + error.getMessage());

                        }
                    });
                }
            }
        }
    }
}