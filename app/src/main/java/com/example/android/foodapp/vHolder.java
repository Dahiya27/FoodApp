package com.example.android.foodapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class vHolder extends RecyclerView.ViewHolder {

    ImageView img;
    TextView t1,t2;
    public vHolder(@NonNull View itemView) {
        super(itemView);
        img = (ImageView)itemView.findViewById(R.id.img1);
        t1 = (TextView)itemView.findViewById(R.id.t1);
        t2 = (TextView)itemView.findViewById(R.id.t2);

    }
}
