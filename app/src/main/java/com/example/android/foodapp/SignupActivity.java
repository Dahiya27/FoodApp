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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        EditText emailid = (EditText) findViewById(R.id.emailid);
        EditText password = (EditText) findViewById(R.id.password);
        Button signup_button = (Button) findViewById(R.id.signup_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_emailid = emailid.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_emailid) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(SignupActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else if(txt_password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Password Too Short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_emailid, txt_password);
                }
            }
        });
    }

    private void registerUser(String emailid,  String password){
        mAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignupActivity.this, "Signup failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}