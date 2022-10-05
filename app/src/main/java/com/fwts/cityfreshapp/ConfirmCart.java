package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fwts.cityfreshapp.Model.PreviousOrderUpload;
import com.fwts.cityfreshapp.adapter.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.paperdb.Paper;

public class ConfirmCart extends AppCompatActivity {
    ImageView bck;
    TextView pcost, scost, tcost; //Product Cost = pcost; Shipping Cost = scost; Total Cost = tcost;
    TextView uname, uaddress, uarea, umobile,dateva,notice; //User Name = uname; User Address = uaddress; User Area = uarea; User Mobile = umobile;
    CheckBox cod;
    Button confirmCart;
    DatabaseReference databaseReference;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM,yyyy");
    private SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
    private String saveCurrentDate,saveCurrentTime;
    RadioButton time1,time2,time3,time4;

    private String timevalue = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cart);
        Paper.init(this);

        //INTENT VALUES PASS FROM CART ACTIVITY
        Intent intentString = getIntent();
        String cost = intentString.getStringExtra("cost");
        int productCost = Integer.parseInt(cost);
        //PAPER VALUES SAVE INSIDE LOCAL DATA
        String name = Paper.book().read("name");
        String address = Paper.book().read("address");
        String area = Paper.book().read("area");
        String value = Paper.book().read("phone");

        //INITIALIZE ACTIVITY
        bck = (ImageView) findViewById(R.id.bck);
        pcost = (TextView) findViewById(R.id.pcost);
        scost = (TextView) findViewById(R.id.scost);
        tcost = (TextView) findViewById(R.id.tcost);
        dateva = (TextView) findViewById(R.id.dateva);
        //dateva.setText(currentDate.format(""));
       // saveCurrentDate = currentDate.format(calendar.getTime());
       // saveCurrentTime = currentTime.format(calendar.getTime());

        uname = (TextView) findViewById(R.id.uname);
        uaddress = (TextView) findViewById(R.id.uaddress);
        uarea = (TextView) findViewById(R.id.uarea);
        umobile = (TextView) findViewById(R.id.umobile);
        cod = (CheckBox) findViewById(R.id.cod);
        confirmCart = (Button) findViewById(R.id.orderconfirm);
        notice = (TextView) findViewById(R.id.notice);
        notice.setVisibility(View.INVISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(value);
        dateva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ConfirmCart.this);
                dialog.setContentView(R.layout.datepickerdialog);
                Button done = dialog.findViewById(R.id.donebtn);
                CalendarView calendarView = dialog.findViewById(R.id.datepickerdialog);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long valueDate =  calendarView.getDate();
                        Date currentDate = new Date(valueDate);
                        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
                        String strDate = dateFormat.format(currentDate);
                        dateva.setText(strDate);
                        dialog.dismiss();


                    }
                });

                dialog.show();

            }
        });
        time1 = (RadioButton) findViewById(R.id.time1);
        time2 = (RadioButton) findViewById(R.id.time2);
        time3 = (RadioButton) findViewById(R.id.time3);
        time4 = (RadioButton) findViewById(R.id.time4);


        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1.setSelected(true);
                time1.setChecked(true);
                time2.setChecked(false);
                time2.setSelected(false);
                time3.setChecked(false);
                time3.setSelected(false);
                time4.setChecked(false);
                time4.setSelected(false);

                timevalue = "Any Time";

                Toast.makeText(ConfirmCart.this, timevalue, Toast.LENGTH_SHORT).show();
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time1.setSelected(false);
                time1.setChecked(false);
                time2.setChecked(true);
                time2.setSelected(true);
                time3.setChecked(false);
                time3.setSelected(false);
                time4.setChecked(false);
                time4.setSelected(false);

                timevalue = "06:00 Am - 08:00 Am";
                Toast.makeText(ConfirmCart.this, timevalue, Toast.LENGTH_SHORT).show();

            }
        });

        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time1.setSelected(false);
                time1.setChecked(false);
                time2.setChecked(false);
                time2.setSelected(false);
                time3.setChecked(true);
                time3.setSelected(true);
                time4.setChecked(false);
                time4.setSelected(false);

                timevalue = "08:00 Am - 10:00 Am";
                Toast.makeText(ConfirmCart.this, timevalue, Toast.LENGTH_SHORT).show();
            }
        });

        time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time1.setSelected(false);
                time1.setChecked(false);
                time2.setChecked(false);
                time2.setSelected(false);
                time3.setChecked(false);
                time3.setSelected(false);
                time4.setChecked(true);
                time4.setSelected(true);

                timevalue = "10:00 Am - 12:00 Pm";
                Toast.makeText(ConfirmCart.this, timevalue, Toast.LENGTH_SHORT).show();
            }
        });



        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmCart.this,CartActivity.class);
                startActivity(intent);
            }
        });


        if (productCost<199){
            pcost.setText("Rs. "+cost);
            int shippingCost = 50;
            int finalCost = productCost + shippingCost;
            scost.setText("Rs. 50");
            tcost.setText("Rs. "+String.valueOf(finalCost));
            pcost.setTextColor(Color.parseColor("#880808"));
            tcost.setTextColor(Color.parseColor("#880808"));
            notice.setVisibility(View.VISIBLE);
            int restProductCost = 200 - productCost;
            notice.setText("To get the free delivery, please add items of worth Rs."+String.valueOf(restProductCost)+" into your cart.");

        }else if (productCost > 200 || productCost == 200){
            pcost.setText("Rs. "+cost);
            scost.setText("Rs. 0");
            tcost.setText("Rs. "+cost);
            notice.setVisibility(View.INVISIBLE);
        }

        uname.setText(name);
        uaddress.setText(address);
        umobile.setText(value);
        uarea.setText(area);

        confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cod.isChecked()){
                    Toast.makeText(ConfirmCart.this, "Please select the payment method", Toast.LENGTH_SHORT).show();
                    cod.setError("Please select the payment method");
                    cod.requestFocus();
                }else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
                    databaseReference.child(value).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //UpdateValueOfPage(timevalue);
                                Toast.makeText(ConfirmCart.this, "Your Cart is confirm. Thanks for order", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfirmCart.this,Confirms.class);
                                intent.putExtra("cartoption","successful");
                                startActivity(intent);
                                finish();



                            }
                        }

                        private void UpdateValueOfPage(String timevalue) {
                            saveCurrentDate = currentDate.format(calendar.getTime());
                            saveCurrentTime = currentTime.format(calendar.getTime());
                            Toast.makeText(ConfirmCart.this,timevalue, Toast.LENGTH_SHORT).show();
                            String dateAndTime = timevalue;//saveCurrentDate +" "+ saveCurrentTime;
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("Cart").child(value);
                            data.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snap:dataSnapshot.getChildren()){
                                        CartAdapter cartAdapter = snap.getValue(CartAdapter.class);
                                        String nameOfProduct = cartAdapter.getProductName();
                                        String qtyOfProduct = cartAdapter.getProductQty();
                                        String priceOfProduct = cartAdapter.getTotalPrice();
                                        DatabaseReference dataR = FirebaseDatabase.getInstance().getReference("Previous").child(value);
                                        PreviousOrderUpload previousOrderUpload = new PreviousOrderUpload(dateAndTime,nameOfProduct
                                                ,qtyOfProduct,priceOfProduct
                                        );

                                        dataR.push().setValue(previousOrderUpload);

//95687088

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });


                }


            }
        });
    }
}