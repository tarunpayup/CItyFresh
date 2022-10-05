package com.fwts.cityfreshapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fwts.cityfreshapp.Model.ProductModel;
import com.fwts.cityfreshapp.Model.Upload;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Addproduct extends AppCompatActivity {
    private static final int GalleryPick = 1;
    ImageView addNewProduct;
    TextView categoryName;
    EditText productName, productDescription, productPrice, productQuantity, productUnit;
    Uri imageuri;
    Button addNewProductBtn;
    StorageReference reference;
    DatabaseReference databaseReference;
    ProductModel productModel;
    String imageName = "product_image";
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        Intent cat = getIntent();
        String catResult = cat.getStringExtra("category");

        //Picture,name,quantity,price
        databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(catResult).push();
        addNewProduct = (ImageView) findViewById(R.id.select_product_image);
        categoryName = (TextView) findViewById(R.id.categoryName);
        productName = (EditText) findViewById(R.id.product_name);
        productDescription = (EditText) findViewById(R.id.product_description);
        productPrice = (EditText) findViewById(R.id.product_price);
        productQuantity = (EditText) findViewById(R.id.qty);
        addNewProductBtn = (Button) findViewById(R.id.add_new_product_btn);
        categoryName.setText(catResult);
        productUnit = (EditText) findViewById(R.id.unit);
        loading = new ProgressDialog(this);
        imageName = "img_" + categoryName.getText().toString().trim()+"_"+System.currentTimeMillis()+ ".png";
        reference = FirebaseStorage.getInstance().getReference(imageName);


        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();

            }
        });


    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            addNewProduct.setImageURI(imageuri);

        }
    }

    private void ValidateProductData() {
        String name = productName.getText().toString().trim();
        String description = productDescription.getText().toString().trim().replaceAll("\\s", "");
        String price = productPrice.getText().toString().trim();
        String quantity = productQuantity.getText().toString().trim();
        String unit = productUnit.getText().toString().trim();

        if (name.equals("") || description.equals("") || price.equals("") || quantity.equals("") || imageuri == null) {
            Toast.makeText(this, "Name,Description,Price,Image or Quantity cannot be blank", Toast.LENGTH_SHORT).show();
        } else {
            AddProductToFirebaseDatabase(name, description, price, quantity, unit, imageuri);
        }


    }

    private void AddProductToFirebaseDatabase(String name, String description, String price, String quantity, String unit, Uri imageUri) {
        productModel = new ProductModel();
        String productId = (categoryName.getText().toString().trim() + name + description + price + quantity + "per" + unit).replace("\\s", "");
        Toast.makeText(this, productId, Toast.LENGTH_SHORT).show();
        loading.setTitle("Uploading Product");
        addDataToFirebase(productId, name, price, quantity, unit, description, imageUri.toString());

    }

    private void addDataToFirebase(String product_id, String name, String price, String qty, String unit, String desc, String image) {
        loading.show();
        reference.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Upload upload = new Upload(imageName, downloadUrl.toString());
                                productModel.setId(product_id);
                                productModel.setP_name(name);
                                productModel.setP_price(price);
                                productModel.setP_qty(qty);
                                productModel.setP_unit(unit);
                                productModel.setDesc(desc);
                                productModel.setImage(downloadUrl.toString());
                                productModel.setCategoryType(categoryName.getText().toString().trim());
                                // we are use add value event listener method
                                // which is called with database reference.
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        // inside the method of on Data change we are setting
                                        // our object class to our database reference.
                                        // data base reference will sends data to firebase.
                                        databaseReference.setValue(productModel);
                                        loading.dismiss();
                                        productUnit.setText("");
                                        productQuantity.setText("");
                                        productDescription.setText("");
                                        productName.setText("");
                                        productPrice.setText("");
                                        addNewProduct.setImageDrawable(getResources().getDrawable(R.drawable.addproductmain));
                                        Toast.makeText(Addproduct.this, "data added", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        // if the data is not added or it is cancelled then
                                        // we are displaying a failure toast message.
                                        Toast.makeText(Addproduct.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    }
                });


    }
}