package com.fwts.cityfreshapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.locks.Lock;

public class Lockscreen extends AppCompatActivity {
   private TextInputLayout lockscreenno;
    private Button adloginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);

        lockscreenno=(TextInputLayout) findViewById(R.id.lockscreenNo);

        adloginBtn = (Button) findViewById(R.id.adminloginbtn);


        adloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = lockscreenno.getEditText().getText().toString().trim();
                if (name.equals("admin")){
                        Intent intent = new Intent(Lockscreen.this,AdminDashboard.class);
                        startActivity(intent);
                        finish();
                }else if (name.equals("delivery")){
                    Intent intent = new Intent(Lockscreen.this,AdminDashboard.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Lockscreen.this, "Invalid Username or Password...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}