package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fwts.cityfreshapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {
    private String nameOfUser, userNumber, shareLink,areaName,addressOfUser,landmarkOfUser,pwdOfUser;
    private TextView nameusr, phoneusr,logoutBtntext;
    private ImageView logoutBtnImg;
    private CardView aboutus, cartItems, orders, share, rate, logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Paper.init(this);
        nameOfUser = Paper.book().read("name");
        userNumber = Paper.book().read("phone");
        areaName = Paper.book().read("area");
        addressOfUser = Paper.book().read("address");
        landmarkOfUser = Paper.book().read("landmark");
        pwdOfUser = Paper.book().read("pwde");



        nameusr = (TextView) findViewById(R.id.nameuser);
        phoneusr = (TextView) findViewById(R.id.phoneuser);

        aboutus = (CardView) findViewById(R.id.aboutus);
        cartItems = (CardView) findViewById(R.id.cartItems);
        orders = (CardView) findViewById(R.id.orders);
        share = (CardView) findViewById(R.id.share);
        rate = (CardView) findViewById(R.id.rate);

        logoutBtn = (CardView) findViewById(R.id.logoutBtn);
        shareLink = "https://play.google.com/store/apps/details?id=com.fwts.cityfreshapp";
        logoutBtntext = (TextView) findViewById(R.id.logoutBtntext);
        logoutBtnImg = (ImageView) findViewById(R.id.logoutBtnImg);




        if (userNumber != null){
            nameusr.setText("Hi "+nameOfUser+" ,");
            phoneusr.setText(userNumber);
            logoutBtnImg.setImageResource(R.drawable.logout);
            logoutBtntext.setText("Logout");
        }else if (userNumber == null){
            nameusr.setText("Please login to your account...");
            phoneusr.setVisibility(View.INVISIBLE);
            logoutBtnImg.setImageResource(R.drawable.login);
            logoutBtntext.setText("Login");
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNumber != null){
                    Paper.book().destroy();
                    Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else if (userNumber == null){
                    Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(intent);

                }
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNumber != null){
                    Dialog userPopup;
                    userPopup = new Dialog(ProfileActivity.this);
                    userPopup.setContentView(R.layout.edituserdetails);
                    TextView closeEdit = userPopup.findViewById(R.id.editclosebtn);
                    Button submitBtn = userPopup.findViewById(R.id.submitedit);
                    TextInputLayout editname = userPopup.findViewById(R.id.editname);
                    TextInputLayout editaddress = userPopup.findViewById(R.id.editaddress);
                    TextInputLayout autoEditCompleteText = userPopup.findViewById(R.id.autoEditCompleteText);
                    AutoCompleteTextView autoAdapEditCompleteText = userPopup.findViewById(R.id.autoAdapEditCompleteText);
                    TextInputLayout editaddresslandmark = userPopup.findViewById(R.id.editaddresslandmark);
                    TextInputLayout edituserPwd = userPopup.findViewById(R.id.edituserPwd);
                    //Display values
                    TextInputEditText nameeditanddisplay = userPopup.findViewById(R.id.nameeditanddisplay);
                    TextInputEditText addresseditanddisplay = userPopup.findViewById(R.id.addresseditanddisplay);
                    TextInputEditText landmarkeditanddisplay = userPopup.findViewById(R.id.landmarkeditanddisplay);
                    TextInputEditText pwdeditanddisplay = userPopup.findViewById(R.id.pwdeditanddisplay);

                    nameeditanddisplay.setText(nameOfUser);
                    addresseditanddisplay.setText(addressOfUser);
                    autoAdapEditCompleteText.setText(areaName);
                    landmarkeditanddisplay.setText(landmarkOfUser);
                    pwdeditanddisplay.setText(pwdOfUser);



                    userPopup.setCanceledOnTouchOutside(false);
                    ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("We are verifying and Updating the data");



                    String[] items = {"Govind Nagar","Hanspuram","Kidwai Nagar","Naubasta","Yashoda Nagar"};

                    ArrayAdapter<String> adapterItems;

                    adapterItems = new ArrayAdapter<String>(ProfileActivity.this,R.layout.list_items,items);

                    autoAdapEditCompleteText.setAdapter(adapterItems);


                    closeEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userPopup.cancel();
                        }
                    });

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            progressDialog.show();
                            String name = editname.getEditText().getText().toString().trim();
                            String address = editaddress.getEditText().getText().toString().trim();
                            String area = autoEditCompleteText.getEditText().getText().toString().trim();
                            String landmark = editaddresslandmark.getEditText().getText().toString().trim();
                            String pwd = edituserPwd.getEditText().getText().toString().trim();

                            if (name.equals("") || address.equals("") || area.equals("") || landmark.equals("") || pwd.equals("")){
                                Toast.makeText(ProfileActivity.this, "Please verify the details..", Toast.LENGTH_SHORT).show();
                                editname.requestFocus();
                                editaddress.requestFocus();
                                autoEditCompleteText.requestFocus();
                                editaddresslandmark.requestFocus();
                                edituserPwd.setError("Please enter password");
                                edituserPwd.requestFocus();
                                progressDialog.dismiss();
                            }else{
                                SubmitDetailsToFirebase(name,address,area,landmark,pwd);
                            }
                        }

                        private void SubmitDetailsToFirebase(String name, String address, String area, String landmark, String pwd) {


                            String updatedAddressOfUser = address;


                            HashMap Userdata = new HashMap();
                            Userdata.put("name",name);
                            Userdata.put("address",updatedAddressOfUser);
                            Userdata.put("pwd",pwd);
                            Userdata.put("area",area);
                            Userdata.put("landmark",landmark);

                            final DatabaseReference data;
                            data = FirebaseDatabase.getInstance().getReference("Users");
                            data.child(userNumber).updateChildren(Userdata).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ProfileActivity.this, "Successfully Update Details", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        userPopup.cancel();
                                        Paper.book().destroy();
                                        VerifyYourProfile();


                                    }else{
                                        Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });

                    userPopup.show();

                }else{
                    ViewErrorDialog();
                }

            }
        });

        cartItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userNumber != null){
                    Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                    startActivity(intent);

                }else if (userNumber == null){
                    ViewErrorDialog();
                }
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNumber != null){
                    Intent intent = new Intent(ProfileActivity.this,MyTotalProducts.class);
                    startActivity(intent);
                }else if (userNumber == null){
                    ViewErrorDialog();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Let's order fresh fruits & vegetables hassle free by City Fresh. To Download click on the link given below:- \n"+shareLink);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.fwts.cityfreshapp")));
                }catch (ActivityNotFoundException e){
                    Uri.parse("https://play.google.com/store/apps/details?id=com.fwts.cityfreshapp");
                }
            }
        });


    }

    private void VerifyYourProfile() {
        Dialog verifyProfile;
        verifyProfile = new Dialog(ProfileActivity.this);
        verifyProfile.setContentView(R.layout.verifyprofile);
        verifyProfile.setCanceledOnTouchOutside(false);
        Button verifyBtn = verifyProfile.findViewById(R.id.verify);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        verifyProfile.show();



    }

    private void ViewErrorDialog() {
        Dialog popuperror;
        popuperror = new Dialog(ProfileActivity.this);
        popuperror.setContentView(R.layout.error);
        Button ok = popuperror.findViewById(R.id.okexit);
        TextView headingpopup = popuperror.findViewById(R.id.headingpopup);

        if (userNumber == null){
            headingpopup.setText("Please Login First...");
        }




        popuperror.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popuperror.cancel();
            }
        });
    }
}