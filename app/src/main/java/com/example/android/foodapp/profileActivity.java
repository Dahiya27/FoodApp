package com.example.android.foodapp;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.squareup.picasso.Picasso;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.EventLogTags;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class profileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth f;
    private TextView fname;
    private FirebaseFirestore fstore;
    private TextView femail;
    private TextView fstate;
    private Button editButton;
    private TextView faddress;
    private Button Locate;
    private TextView fgender;
    private TextView fdescription;
    private EditText Name;
    private Spinner State;
    private Spinner Gender;
    private EditText Description;
    private ImageView myimage;

String address1;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    List<Address> addresses;
   private Button Update;
   private Uri filepath;
    String downloadurl;
    String urli;
    private static final int Gallery_pick = 1000;
    private static final int Permission_code = 1001;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        f = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        myimage=findViewById(R.id.image);
        fname = findViewById(R.id.et_name_cp);
        femail = findViewById(R.id.et_Email_cp);
        fstate = findViewById(R.id.state);
        faddress = findViewById(R.id.address);
        Locate = findViewById(R.id.locate);
        fgender=findViewById(R.id.gender);
        fdescription=findViewById(R.id.Desc);
        editButton=findViewById(R.id.btn);
        Update=findViewById(R.id.updateButton);
        Name=findViewById(R.id.name);
        State=findViewById(R.id.pstate);
        Gender=findViewById(R.id.gndr);
        Description=findViewById(R.id.description);
        storage=FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Name.setVisibility(View.GONE);
        State.setVisibility(View.GONE);
        Gender.setVisibility(View.GONE);
        Description.setVisibility(View.GONE);
        Update.setVisibility(View.GONE);
        Locate.setVisibility(View.GONE);
        String id = f.getCurrentUser().getUid().toString();
        myimage.setEnabled(false);

        fstore.collection("users").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            fname.setText(documentSnapshot.getString("FullName"));
                            femail.setText(documentSnapshot.getString("Email"));
                            fstate.setText(documentSnapshot.getString("stateName"));
                            fgender.setText(documentSnapshot.getString("genderType"));
                            fdescription.setText(documentSnapshot.getString("Description"));
                            faddress.setText(documentSnapshot.getString("Address"));
                            urli=documentSnapshot.getString("image");
                            if(documentSnapshot.getString("image").equals("noImage")){
                                myimage.setImageResource(R.drawable.burger__fastfood__food__hamburger__junkfood__beef__drink_512);
                            }
                            else{
                                Picasso.get().load(documentSnapshot.getString("image")).into(myimage);
                            }



                        }
                    }
                });


        myimage.setOnClickListener(new View.OnClickListener() {
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
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Name.getText().toString();
                String stateName=State.getSelectedItem().toString();
                int state=State.getSelectedItemPosition();
                String genderType=Gender.getSelectedItem().toString();
                int gender=Gender.getSelectedItemPosition();
                String Description1=Description.getText().toString();
                String address=faddress.getText().toString();
                Name.setVisibility(View.GONE);
                State.setVisibility(View.GONE);
                Gender.setVisibility(View.GONE);
                Description.setVisibility(View.GONE);
                Update.setVisibility(View.GONE);
String email=femail.getText().toString();

                UpdateUser(name,email,stateName,genderType,Description1,address,gender,state);
                uploadImage();

                myimage.setEnabled(false);


            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Gender.setAdapter(adapter1);
        Gender.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        State.setAdapter(adapter);
        State.setOnItemSelectedListener(this);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myimage.setEnabled(true);
                fname.setVisibility(View.GONE);
                fstate.setVisibility(View.GONE);
                fgender.setVisibility(View.GONE);
                fdescription.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);


                String id = f.getCurrentUser().getUid().toString();

                fstore.collection("users").document(id).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Name.setText(documentSnapshot.getString("FullName"));
                                    State.setSelection(documentSnapshot.getLong("State").intValue());
                                    Gender.setSelection(documentSnapshot.getLong("gender").intValue());
                                    Description.setText(documentSnapshot.getString("Description"));
                                    Name.setVisibility(View.VISIBLE);
                                    State.setVisibility(View.VISIBLE);
                                    Gender.setVisibility(View.VISIBLE);
                                    Description.setVisibility(View.VISIBLE);
                                    Update.setVisibility(View.VISIBLE);
                                    Locate.setVisibility(View.VISIBLE);


                                }
                            }
                        });


            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(profileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                } else {
                    ActivityCompat.requestPermissions(profileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        }
private void pickImageFromGallery(){
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_pick && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                myimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateUser(String name,String email, String stateName, String genderType, String Description, String address, int gender, int state){
        String id=f.getCurrentUser().getUid();
        Map<String,Object> doc=new HashMap<>();
        doc.put("Id",id);
        doc.put("FullName",name);
        doc.put("Email",email);
        doc.put("State",state);
        doc.put("gender",gender);
        doc.put("stateName",stateName);
        doc.put("genderType",genderType);
        doc.put("Description",Description);
        doc.put("image",downloadurl);
        doc.put("Address",address);
fstore.collection("users").document(id).set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
   Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
    }
});

        fname.setText(name);
        fstate.setText(stateName);
        fgender.setText(genderType);
        fdescription.setText(Description);
        fname.setVisibility(View.VISIBLE);
        fstate.setVisibility(View.VISIBLE);
        fgender.setVisibility(View.VISIBLE);
        fdescription.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        Locate.setVisibility(View.GONE);


    }

    private void getlocation() {
        ProgressDialog progressDialog
                = new ProgressDialog(profileActivity.this);
        progressDialog.setTitle("Please wait while we find your location....");
        progressDialog.show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
Location location=task.getResult();

                geocoder = new Geocoder(profileActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    //String area = addresses.get(0).getLocality();
                    //String city = addresses.get(0).getAdminArea();
                   // String country = addresses.get(0).getCountryName();
                   // String postalcode = addresses.get(0).getPostalCode();
                    String Fulladdress = address  ;
                    address1=Fulladdress;
faddress.setText(Fulladdress);
Locate.setVisibility(View.GONE);
progressDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void uploadImage()
    {
        if (filepath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(profileActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filepath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while(!uriTask.isSuccessful());

                                    downloadurl = uriTask.getResult().toString();
                                    if(uriTask.isSuccessful())
                                    {

                                        Savingimage(downloadurl);
                                    }
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(profileActivity.this,
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
                                    .makeText(profileActivity.this,
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
    private void Savingimage(String url) {
        String id = f.getCurrentUser().getUid();
        fstore.collection("users").document(id).update("image", url);
    }
    }
