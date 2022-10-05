package com.fwts.cityfreshapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.fwts.cityfreshapp.adapter.CartAdapter;
import com.fwts.cityfreshapp.adapter.CartTotalQty;
import com.fwts.cityfreshapp.adapter.CartValuePrice;
import com.fwts.cityfreshapp.adapter.CartViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CartViewAdapter cartView;
    ArrayList<CartAdapter> list;
    TextView addMore, totalPrice, totalQty, tvProductCart;
    Button confirmCart;
    String valueOfCart = null;
    int oneProductValue = 0;
    int oneProductCartQty = 0;
    int oneProductTotalPrice = 0;
    int oneProductTotalQty = 0;
    LottieAnimationView emptycartanim;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM,yyyy");
    private SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
    private String saveCurrentDate, saveCurrentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Paper.init(this);
        String userNumber = null;
        String value = Paper.book().read("phone");
        confirmCart = (Button) findViewById(R.id.confirmCart);
        recyclerView = (RecyclerView) findViewById(R.id.displayCartProducts);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(value);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addMore = (TextView) findViewById(R.id.addmore);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        totalQty = (TextView) findViewById(R.id.totalQty);
        tvProductCart = (TextView) findViewById(R.id.tvProductCart);
        emptycartanim = (LottieAnimationView) findViewById(R.id.emptycartanim);

        emptycartanim.setVisibility(View.INVISIBLE);


        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMoreProduct();
            }

            private void AddMoreProduct() {
                Toast.makeText(CartActivity.this, "Add More Products", Toast.LENGTH_SHORT).show();
                Dialog popUpShop;
                popUpShop = new Dialog(CartActivity.this);
                popUpShop.setContentView(R.layout.selectproducts);
                Button buyV = popUpShop.findViewById(R.id.buyV);
                Button buyF = popUpShop.findViewById(R.id.buyF);
                TextView close = popUpShop.findViewById(R.id.buyclosebtn);
                popUpShop.show();

                buyV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(CartActivity.this, ProductDisplayLayoutActivity.class);
                        in.putExtra("type", "Vegetables");
                        startActivity(in);
                    }
                });

                buyF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(CartActivity.this, ProductDisplayLayoutActivity.class);
                        in.putExtra("type", "Fruit");
                        startActivity(in);

                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popUpShop.cancel();
                    }
                });

            }


        });


        list = new ArrayList<>();
        cartView = new CartViewAdapter(this, list);
        recyclerView.setAdapter(cartView);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    CartAdapter cartAdapter = snapshot.getValue(CartAdapter.class);
                    cartAdapter.setKey(key);
                    list.add(cartAdapter);


                }

                cartView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                oneProductTotalPrice = 0;
                oneProductValue = 0;
                oneProductTotalQty = 0;
                oneProductCartQty = 0;


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartValuePrice cartValuePrice = snapshot.getValue(CartValuePrice.class);
                    CartTotalQty cartTotalQty = snapshot.getValue(CartTotalQty.class);
                    oneProductTotalPrice = Integer.valueOf(cartValuePrice.getTotalPrice());
                    oneProductValue = oneProductTotalPrice + oneProductValue;
                    oneProductTotalQty = Integer.valueOf(cartTotalQty.getProductQty());
                    oneProductCartQty = oneProductTotalQty + oneProductCartQty;

                }

                String oneProductValueStr = String.valueOf(oneProductValue);
                totalPrice.setText("Total Price : Rs." + oneProductValueStr);
                totalQty.setText("Total Items : " + oneProductCartQty);
                IfClickOnButton(oneProductValueStr);

                if (oneProductCartQty == 0) {
                    confirmCart.setEnabled(false);
                    confirmCart.setText("Empty Cart");
                    tvProductCart.setText("Empty Cart");
                    recyclerView.setVisibility(View.INVISIBLE);
                    emptycartanim.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void IfClickOnButton(String oneProductValueStr) {
        confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ConfirmCart.class);
                intent.putExtra("cost", oneProductValueStr);
                startActivity(intent);
            }
        });
    }


}