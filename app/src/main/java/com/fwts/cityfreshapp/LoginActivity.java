package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fwts.cityfreshapp.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout loginPwd,loginPhone;
    TextView signUpText,destroy;
    Button signInBtn;
    ProgressDialog loading;
    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPhone = (TextInputLayout) findViewById(R.id.loginPhone);
        loginPwd = (TextInputLayout) findViewById(R.id.loginPwd);
        signInBtn = (Button) findViewById(R.id.loginbtn);
        signUpText = (TextView) findViewById(R.id.newaccount);
        loading = new ProgressDialog(this);
        destroy = (TextView)findViewById(R.id.destroy);
        Paper.init(this);

        destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginPhone.getEditText().getText().toString().trim();
                String pwd = loginPwd.getEditText().getText().toString().trim();

                validationOfUser(username,pwd);
                loading.setTitle("Validating User");
                loading.setMessage("Please wait while we verifying your credentials...");
                loading.setCanceledOnTouchOutside(false);
            }

            private void validationOfUser(String username, String pwd) {
                loading.show();

                if (username.equals("") || pwd.equals("")){
                    loginPhone.setError("Login username must not be empty");
                    loginPhone.requestFocus();
                    loginPwd.setError("Login password must not be empty");
                    loginPwd.requestFocus();
                    loading.dismiss();
                }else if(pwd.length()<6){
                    loginPwd.setError("Length of password must not be lesser than 6");
                    loginPwd.requestFocus();
                    loading.dismiss();
                }else{
                    Toast.makeText(LoginActivity.this, "Validate Successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Verifying credentials in database", Toast.LENGTH_SHORT).show();
                    verifyUserInsideDatabase(username,pwd);

                }
            }

            private void verifyUserInsideDatabase(String username, String pwd) {
                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();

                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(parentDbName).child(username).exists()){
                            User userdata = dataSnapshot.child(parentDbName).child(username).getValue(User.class);
                            if (userdata.getPhone().equals(username)){
                                Toast.makeText(LoginActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                                if (userdata.getPwd().equals(pwd)){
                                    String nameOfUser = dataSnapshot.child(parentDbName).child(username).child("name").getValue(String.class);
                                    String addressOfUser = dataSnapshot.child(parentDbName).child(username).child("address").getValue(String.class);
                                    String areaOfUser = dataSnapshot.child(parentDbName).child(username).child("area").getValue(String.class);
                                    String landmarkOfUser = dataSnapshot.child(parentDbName).child(username).child("landmark").getValue(String.class);
                                    Toast.makeText(LoginActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                    Paper.book().write("phone",username);
                                    Paper.book().write("address",addressOfUser);
                                    Paper.book().write("name",nameOfUser);
                                    Paper.book().write("area",areaOfUser);
                                    Paper.book().write("landmark",landmarkOfUser);
                                    Paper.book().write("pwde",pwd);


                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    loading.dismiss();
                                    Toast.makeText(LoginActivity.this, "Password Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed!...CODE 100:(Credentials not found)", Toast.LENGTH_SHORT).show();
                            loading.dismiss();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, "Error Occured!..."+" Error Code:"+databaseError.getCode()+
                                " Message:"+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

    }
}