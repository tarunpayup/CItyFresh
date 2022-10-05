package com.fwts.cityfreshapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    ImageSlider imageSlider;
   // ImageSlider imageSlider2;
    CardView orderVegetables,orderFruits;
    ImageView logOutUser;
    ImageView imgs;
    ImageView mycart;
    String nameOfUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Paper.init(this);
        Intent intent = getIntent();
            // String userNumber = intent.getStringExtra("username");
        String value = Paper.book().read("phone");
        mycart = (ImageView)findViewById(R.id.mycartv);
        nameOfUser = Paper.book().read("name");
        orderVegetables = (CardView)findViewById(R.id.orderVegetables);
        orderFruits = (CardView)findViewById(R.id.orderFruits);
        logOutUser = (ImageView)findViewById(R.id.loginTxt);
        imageSlider = (ImageSlider)findViewById(R.id.img_slider2);
       // imageSlider2 = (ImageSlider)findViewById(R.id.img_slider2);
        imgs = (ImageView)findViewById(R.id.imgs);



        logOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(inten);
            }
        });


        imgs.setOnClickListener(new View.OnClickListener() {
            int num = 0;
            @Override
            public void onClick(View view) {
                num++;
                Toast.makeText(HomeActivity.this, "Welcome"+num, Toast.LENGTH_SHORT).show();

                if (num==5 || num>5){
                    Intent shifting = new Intent(HomeActivity.this,Lockscreen.class);
                    startActivity(shifting);
                }else{
                }

            }
        });


        orderVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this,ProductDisplayLayoutActivity.class).putExtra("type","Vegetables"));
                //Toast.makeText(HomeActivity.this, "Working : "+value, Toast.LENGTH_SHORT).show();
            }
        });

        orderFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ProductDisplayLayoutActivity.class).putExtra("type","Fruit"));

                //Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
            }
        });
        //LOG OUT USER
        mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value != null){
                    Intent hift = new Intent(HomeActivity.this,CartActivity.class);
                    //Intent hift = new Intent(HomeActivity.this,ProfileActivity.class);
                    startActivity(hift);
                }else if (value == null){
                    Toast.makeText(HomeActivity.this, "You are not login. Please login first", Toast.LENGTH_SHORT).show();
                    Intent inq = new Intent(HomeActivity.this,LoginActivity.class);
                    //Intent inq = new Intent(HomeActivity.this,ProfileActivity.class);
                    startActivity(inq);
                }
            }
        });

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.one,null));
        images.add(new SlideModel(R.drawable.two,null));
        images.add(new SlideModel(R.drawable.three,null));

        imageSlider.setImageList(images);
       // imageSlider2.setImageList(images);
    }

    @Override
    public void onBackPressed() {
        Dialog popclose;
        popclose = new Dialog(HomeActivity.this);
        popclose.setContentView(R.layout.exitpop);
        Button yes = popclose.findViewById(R.id.yes);
        Button no = popclose.findViewById(R.id.no);
        TextView close = popclose.findViewById(R.id.closebtnbye);
        popclose.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popclose.cancel();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                moveTaskToBack(true);

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popclose.cancel();
            }
        });

    }

}