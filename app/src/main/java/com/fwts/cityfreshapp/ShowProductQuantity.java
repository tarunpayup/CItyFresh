package com.fwts.cityfreshapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fwts.cityfreshapp.Model.AdminCartUpload;
import com.fwts.cityfreshapp.Model.CartUpload;
import com.fwts.cityfreshapp.Model.PreviousOrderUpload;
import com.fwts.cityfreshapp.Model.ProductModel;
import com.fwts.cityfreshapp.adapter.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class ShowProductQuantity extends AppCompatActivity {
    private final String Url = "http://www.tarunbansal.co.in/fruitseller/cart.php";
    ImageView increment, decrement;
    TextView numberdisp;
    Button addtocartbtn, updateBtn;
    int count = 0;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat currentDate = new SimpleDateFormat("ddmmyyyy");
    SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
    String saveCurrentDate, saveCurrentTime;
    CartAdapter cartAdapterIntent;
    //Testing - Remove after test to implement it
    ImageView productinformationimage;
    TextView productinformationprice, productinformationunit, productinformationqty, productinformationname;
    ProductModel productModel;
    private String work, valuePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_quantity);
        Paper.init(this);
    }

}