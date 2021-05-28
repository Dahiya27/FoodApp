package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RestroSignUp extends AppCompatActivity {

    TextInputLayout resUsername, resadd, rescont, resemail, respass;
    Button regbutt, regtologinbutt;
    FirebaseAuth fAuth;
    FirebaseFirestore dbroot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restro_sign_up);

        resUsername = findViewById(R.id.restroname);
        resadd = findViewById(R.id.restroaddress);
        rescont = findViewById(R.id.restrocontact);
        resemail = findViewById(R.id.restroemail);
        respass = findViewById(R.id.restropass);
        regbutt = findViewById(R.id.regbutt);
        regtologinbutt = findViewById(R.id.regtologinbutt);
        dbroot =FirebaseFirestore.getInstance();

        fAuth=FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),RestroHome.class));
            finish();
        }

        regtologinbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RestroActivity.class));
            }
        });

        regbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
                String email = resemail.getEditText().getText().toString().trim();
                String pass = respass.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    resemail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    respass.setError("Password is required.");
                    return;
                }
                if(pass.length()<6)
                {
                    respass.setError("Password must be atleast 6 characters.");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RestroSignUp.this, "Restro Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),RestroHome.class));
                        }
                        else
                        {
                            Toast.makeText(RestroSignUp.this, "Error!! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void insertdata(){
        Map<String,String> item=new HashMap<>();
        item.put("RestaurantUserName",resUsername.getEditText().getText().toString().trim());
        item.put("ContactNumber",rescont.getEditText().getText().toString().trim());
        item.put("Address",resadd.getEditText().getText().toString().trim());
        item.put("EmailId",resemail.getEditText().getText().toString().trim());
        dbroot.collection("Restro").add(item)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        resUsername.getEditText().setText("");
                        rescont.getEditText().setText("");
                        resadd.getEditText().setText("");
                        resemail.getEditText().setText("");
                    }
                });

    }
}