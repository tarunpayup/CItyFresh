package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fwts.cityfreshapp.Model.PreviousOrdersModel;
import com.fwts.cityfreshapp.adapter.PreviousAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import io.paperdb.Paper;

public class MyTotalProducts extends AppCompatActivity {
    RecyclerView displayAllOrderProducts;
    DatabaseReference databaseReference;
    PreviousAdapter previousAdapter;
    ArrayList<PreviousOrdersModel> list;
    private String currentPhoneValue = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_total_products);
        Paper.init(this);

        currentPhoneValue = Paper.book().read("phone");

        displayAllOrderProducts = (RecyclerView) findViewById(R.id.displayAllOrderProducts);
        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().
                getReference("Previous").child(currentPhoneValue);

        displayAllOrderProducts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        displayAllOrderProducts.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        previousAdapter = new PreviousAdapter(this,list);
        displayAllOrderProducts.setAdapter(previousAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snp : dataSnapshot.getChildren()){
                    PreviousOrdersModel previousOrdersModel = snp.getValue(PreviousOrdersModel.class);
                    list.add(previousOrdersModel);

                }
                previousAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}