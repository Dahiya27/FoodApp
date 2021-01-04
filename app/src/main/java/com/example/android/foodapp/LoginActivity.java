package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView direct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        EditText emailid = (EditText) findViewById(R.id.emailid);
        EditText password = (EditText) findViewById(R.id.password);
        Button login_button = (Button) findViewById(R.id.login_button);
        Button Show;

        direct=findViewById(R.id.direct);
        Show=findViewById(R.id.show);
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Show.getText().toString().equals("Show")){
                    Show.setText("Hide");
                    password.setTransformationMethod(null);
                } else {
                    Show.setText("Show");
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_emailid = emailid.getText().toString();
                String txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_emailid)) {
                    emailid.setError("Enter EmailId");
                    emailid.requestFocus();
                }
                else if(TextUtils.isEmpty(txt_password)) {
                    password.setError("Enter Password");
                    password.requestFocus();
                }
                else if(!(TextUtils.isEmpty(txt_emailid) && (TextUtils.isEmpty(txt_password)))){
                    loginUser(txt_emailid, txt_password);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });
    }

    private void loginUser(String emailid,  String password) {
        mAuth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Email and password didn't match", Toast.LENGTH_SHORT).show();
                } else {
                    Intent inToHome = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(inToHome);
                }
            }
        });
    }
}