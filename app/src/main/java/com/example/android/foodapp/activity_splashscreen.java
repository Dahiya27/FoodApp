package com.example.android.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class activity_splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 4000;

    ImageView logo,img;
    LottieAnimationView lottieAnimationView;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logo=findViewById(R.id.logo);
        app_name=findViewById(R.id.app_name);
        img=findViewById(R.id.img);
        lottieAnimationView=findViewById(R.id.lottie);

        img.animate().translationY(-5500).setDuration(500).setStartDelay(3000);
        logo.animate().translationY(3000).setDuration(500).setStartDelay(3000);
        app_name.animate().translationY(3000).setDuration(500).setStartDelay(3000);
        lottieAnimationView.animate().translationY(3000).setDuration(500).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity_splashscreen.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}