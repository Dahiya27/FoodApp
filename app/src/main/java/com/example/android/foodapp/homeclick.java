package com.example.android.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class homeclick extends AppCompatActivity {

    ImageView img2;
    TextView t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeclick);
        setTitle("Restaurants");


        img2 = (ImageView)findViewById(R.id.img2);
        t3 = (TextView)findViewById(R.id.t3);
        t4 = (TextView)findViewById(R.id.t4);

        img2.setImageResource(getIntent().getIntExtra("ImageName",0));
        t3.setText(getIntent().getStringExtra("Header"));
        t4.setText(getIntent().getStringExtra("Desc"));
    }
}