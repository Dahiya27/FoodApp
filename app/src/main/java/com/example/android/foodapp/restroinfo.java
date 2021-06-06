package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class restroinfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private EditText name;
private Spinner type;
private ImageView image;
private Button save;
    FirebaseAuth fauth;
    FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filepath;
    private String urli;
    private FirebaseFirestore fstore;
private String rtype;
    private static final int Gallery_pick = 1000;
    private static final int Permission_code = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroinfo);
        name=findViewById(R.id.rname);
        type=findViewById(R.id.rspinner);
        image=findViewById(R.id.rimage);
        save=findViewById(R.id.rsave);
        fauth=FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        fstore=FirebaseFirestore.getInstance();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,Permission_code);
                    }else{
                        pickImageFromGallery();
                    }

                }else{
                    pickImageFromGallery();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("this field shouldn't be left blank ");
                }
                else{
                uploadImage();}
            }
        });

    }
    private void pickImageFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery,Gallery_pick);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Permission_code:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Gallery_pick && data != null) {
            filepath = data.getData();
            Picasso.get().load(filepath).fit().centerCrop(-10).into(image);

        }
    }
    private void uploadImage()
    {
        if (filepath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] data = baos.toByteArray();


            // adding listeners on upload
            // or failure of image
            ref.putBytes(data)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while(!uriTask.isSuccessful());

                                    urli = uriTask.getResult().toString();
                                    if(uriTask.isSuccessful())
                                    {
                                        Savingimage(urli,name.getText().toString(),type.getSelectedItem().toString());




                                    }
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getApplicationContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(restroinfo.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
    private void Savingimage(String url,String name,String type){
        String id=fauth.getCurrentUser().getUid();
        Map<String,Object> doc=new HashMap<>();
        doc.put("Image",url);
        doc.put("Title",name);
        doc.put("Type",type);

        fstore.collection("Restaurants").document(id).set(doc);
        startActivity(new Intent(this,RestroHome.class));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //rtype=type.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}