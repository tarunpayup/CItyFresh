package com.fwts.cityfreshapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Confirms extends AppCompatActivity {
    private String v,a;
    private TextView disnote,disnote2;
    Button shopmore,myorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirms);
        Intent intent = getIntent();
        a = intent.getStringExtra("editoption");
        v = intent.getStringExtra("cartoption");
        disnote = (TextView) findViewById(R.id.disnote);
        disnote2 = (TextView) findViewById(R.id.disnote2);
        shopmore = (Button) findViewById(R.id.shopmore);
        myorders = (Button) findViewById(R.id.myorders);
        shopmore.setVisibility(View.INVISIBLE);
        myorders.setVisibility(View.INVISIBLE);

        if(a != null){
            disnote.setText("All Set");
            disnote2.setText("Changes are successfully done...");

        }else if (v !=null){
            disnote.setText("Thankyou");
            disnote2.setText("Your order placed successfully");
            shopmore.setVisibility(View.VISIBLE);
            myorders.setVisibility(View.VISIBLE);

        }

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt = new Intent(Confirms.this,MyTotalProducts.class);
                startActivity(intentt);

            }
        });
        if (a != null && v == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(Confirms.this,AdminDashboard.class);
                    intent.putExtra("toption","a");
                    startActivity(intent);
                    finish();
                }
            },3000);
        }

        shopmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog popUpShop;
                popUpShop = new Dialog(Confirms.this);
                popUpShop.setContentView(R.layout.selectproducts);
                Button buyV = popUpShop.findViewById(R.id.buyV);
                Button buyF = popUpShop.findViewById(R.id.buyF);
                TextView close = popUpShop.findViewById(R.id.buyclosebtn);
                popUpShop.show();

                buyV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(Confirms.this,ProductDisplayLayoutActivity.class);
                        in.putExtra("type","Vegetables");
                        startActivity(in);
                    }
                });

                buyF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(Confirms.this,ProductDisplayLayoutActivity.class);
                        in.putExtra("type","Fruit");
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



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Confirms.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}