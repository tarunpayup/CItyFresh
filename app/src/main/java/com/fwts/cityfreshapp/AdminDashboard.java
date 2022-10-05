package com.fwts.cityfreshapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashboard extends AppCompatActivity {
    CardView addProduct, getOrders, deliveryStatus, editBtn;
    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Intent intent = getIntent();
        a = intent.getStringExtra("toption");
        addProduct = (CardView) findViewById(R.id.addproduct);
        getOrders = (CardView) findViewById(R.id.getorders);
        deliveryStatus = (CardView) findViewById(R.id.deliverystatus);
        editBtn = (CardView) findViewById(R.id.edit_product);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog editItems;
                    editItems = new Dialog(AdminDashboard.this);
                    editItems.setContentView(R.layout.editcustomlayout);
                    Button editV = editItems.findViewById(R.id.editV);
                    Button editF = editItems.findViewById(R.id.editF);
                    TextView close = editItems.findViewById(R.id.editclosebtn);
                    editItems.show();

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editItems.cancel();
                        }
                    });
                    editV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(AdminDashboard.this,ProductDisplayLayoutActivity.class);
                            intent.putExtra("type","Vegetables");
                            intent.putExtra("work","editVegetable");
                            startActivity(intent);

                        }
                    });

                    editF.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(AdminDashboard.this,ProductDisplayLayoutActivity.class);
                            intent.putExtra("type","Fruit");
                            intent.putExtra("work","editFruit");
                            startActivity(intent);
                        }
                    });




                }
            });

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog popupCategory;
                    popupCategory = new Dialog(AdminDashboard.this);
                    popupCategory.setContentView(R.layout.customlinkpopupselectcategory);
                    Button veges = popupCategory.findViewById(R.id.veges);
                    Button fruits = popupCategory.findViewById(R.id.fruits);
                    TextView closebtn = popupCategory.findViewById(R.id.closebtn);

                    closebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AdminDashboard.this, "Category Popup Closes", Toast.LENGTH_SHORT).show();
                            popupCategory.cancel();
                        }
                    });

                    veges.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AdminDashboard.this, "Adding Vegetables", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminDashboard.this, Addproduct.class);
                            intent.putExtra("category", "Vegetables");
                            startActivity(intent);
                        }
                    });

                    fruits.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AdminDashboard.this, "Adding Fruits", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminDashboard.this, Addproduct.class);
                            intent.putExtra("category", "Fruits");
                            startActivity(intent);
                        }
                    });

                    popupCategory.show();



/*                Intent intent = new Intent(AdminDashboard.this,Addproduct.class);
                startActivity(intent);*/
                }
            });

            getOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminDashboard.this, AdminOrders.class);
                    startActivity(intent);
                }
            });

            deliveryStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminDashboard.this, DeliveryStatus.class);
                    startActivity(intent);
                }
            });


        if (a != null){
            Dialog editItems;
            editItems = new Dialog(AdminDashboard.this);
            editItems.setContentView(R.layout.editcustomlayout);
            Button editV = editItems.findViewById(R.id.editV);
            Button editF = editItems.findViewById(R.id.editF);
            TextView close = editItems.findViewById(R.id.editclosebtn);
            editItems.show();

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editItems.cancel();
                }
            });
            editV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminDashboard.this,ProductDisplayLayoutActivity.class);
                    intent.putExtra("type","Vegetables");
                    intent.putExtra("work","editVegetable");
                    startActivity(intent);

                }
            });

            editF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AdminDashboard.this,ProductDisplayLayoutActivity.class);
                    intent.putExtra("type","Fruit");
                    intent.putExtra("work","editFruit");
                    startActivity(intent);
                }
            });
        }


        }


    }
