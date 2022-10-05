package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class SignupActivity extends AppCompatActivity {
    private TextView loginActivity;
    private Button signUpBtn;
    private TextInputLayout nameOfUser,phoneOfUser,addressOfUser,addressLandmark,pwdOfUser;
    private ProgressDialog loadingBar;
    String[] items = {"Govind Nagar","Hanspuram","Kidwai Nagar","Naubasta","Yashoda Nagar"};
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Paper.init(this);
        autoCompleteText = (AutoCompleteTextView)findViewById(R.id.autoCompleteText);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_items,items);
        autoCompleteText.setAdapter(adapterItems);
        loginActivity = (TextView) findViewById(R.id.loginActivity);
        signUpBtn = (Button) findViewById(R.id.signupbtn);
        nameOfUser = (TextInputLayout)findViewById(R.id.nameofuser);
        phoneOfUser = (TextInputLayout)findViewById(R.id.phone);
        addressOfUser = (TextInputLayout)findViewById(R.id.email);
        addressLandmark = (TextInputLayout)findViewById(R.id.landmark);
        pwdOfUser = (TextInputLayout)findViewById(R.id.userPwd);
        loadingBar = new ProgressDialog(this);


            autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getItemAtPosition(i).toString();
                }
            });


            loginActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SignupActivity.this, "Validating your credentials", Toast.LENGTH_SHORT).show();
                    validatingCredentials();

                }

                private void validatingCredentials() {
                    String name = nameOfUser.getEditText().getText().toString().trim();
                    String phone = phoneOfUser.getEditText().getText().toString().trim();
                    String address = addressOfUser.getEditText().getText().toString().trim();
                    String pwd = pwdOfUser.getEditText().getText().toString().trim();
                    String areaName = autoCompleteText.getEditableText().toString().trim();
                    String areaLandmark = addressLandmark.getEditText().getText().toString().trim();

                    if (name.equals("") || phone.equals("") || address.equals("") || pwd.equals("") ||
                            areaName.equals("") || areaLandmark.equals("")){
                        nameOfUser.setError("Name cannot be blank. Please check it.");
                        nameOfUser.requestFocus();
                        phoneOfUser.setError("Phone cannot be blank. Please check it.");
                        phoneOfUser.requestFocus();
                        addressOfUser.setError("Address cannot be blank. Please check it.");
                        addressOfUser.requestFocus();
                        pwdOfUser.setError("Password cannot be blank. Please check it.");
                        pwdOfUser.requestFocus();
                        autoCompleteText.setError("Area Cannot be Empty");
                        autoCompleteText.requestFocus();
                        addressLandmark.setError("Area Cannot be Empty");
                        addressLandmark.requestFocus();

                    }else if (phone.length()<10){
                        phoneOfUser.setError("Phone Number must be of 10 digits");
                        phoneOfUser.requestFocus();

                    }else if (pwd.length()<6){
                        pwdOfUser.setError("Password cannot be lesser than 6 digits");
                        pwdOfUser.requestFocus();

                    }else{
                        createUserAccount(name,phone,address,pwd,areaName,areaLandmark);
                        Toast.makeText(SignupActivity.this, "All credentials are ok.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignupActivity.this, "Creating your account", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignupActivity.this, "Checking the account", Toast.LENGTH_SHORT).show();
                    }

                }

                private void createUserAccount(String name, String phone, String address, String pwd, String areaName, String areaLandmark) {
                    loadingBar.setTitle("Creating Account");
                    loadingBar.setMessage("Please wait, while we checking your credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    final DatabaseReference Rootref;
                    Rootref = FirebaseDatabase.getInstance().getReference();

                    Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!(dataSnapshot.child("Users").child(phone).exists())){
                                HashMap<String,Object> userDataName = new HashMap<>();
                                userDataName.put("phone",phone);
                                userDataName.put("name",name);
                                userDataName.put("address",address+", "+areaName+", "+ areaLandmark +", Kanpur, UP");
                                userDataName.put("area",areaName);
                                userDataName.put("pwd",pwd);
                                userDataName.put("landmark",areaLandmark);

                                Rootref.child("Users").child(phone).updateChildren(userDataName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            loadingBar.dismiss();

                                            Dialog dialog = new Dialog(SignupActivity.this);
                                            dialog.setContentView(R.layout.accountcreated);
                                            Button verifyme = dialog.findViewById(R.id.verifyaccount);
                                            verifyme.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

                                            dialog.show();
                                            dialog.setCancelable(false);

                                        }else{
                                            loadingBar.dismiss();
                                            Toast.makeText(SignupActivity.this, "Signup not successful", Toast.LENGTH_SHORT).show();
                                        }
                                    }//9415224046 - Vikas Fufaji
                                });


                            }else{
                                loadingBar.dismiss();
                                Dialog dialog = new Dialog(SignupActivity.this);
                                dialog.setContentView(R.layout.duplicatephone);
                                Button ok = dialog.findViewById(R.id.ok);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                dialog.setCancelable(false);
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