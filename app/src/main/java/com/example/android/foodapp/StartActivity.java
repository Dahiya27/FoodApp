package com.example.android.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, SignupActivity.class));
        });
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });
    }
}