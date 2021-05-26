package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RestroActivity extends AppCompatActivity {

    Button callsignup,loginbutt;
    TextInputLayout usernam, password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_restro);
        callsignup=findViewById(R.id.newuser);
        usernam=findViewById(R.id.username);
        password=findViewById(R.id.pass);
        loginbutt=findViewById(R.id.loginbutt);
        fAuth=FirebaseAuth.getInstance();

        callsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RestroSignUp.class));
                finish();
            }
        });

        loginbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernam.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    usernam.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required.");
                    return;
                }
                if (pass.length() < 6) {
                    password.setError("Password must be atleast 6 characters.");
                    return;
                }
                fAuth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RestroActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),RestroHome.class));
                        }
                        else
                        {
                            Toast.makeText(RestroActivity.this, "Error!! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
}