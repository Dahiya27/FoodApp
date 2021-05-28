package com.example.android.foodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.foodapp.ui.home.ViewDishesFragment;

import java.util.ArrayList;


public class ViewDishAdapter extends RecyclerView.Adapter<ViewDishAdapter.myViewHolder> {


    ArrayList<Membr> dataList;
    ViewDishesFragment context;

    public ViewDishAdapter(ArrayList<Membr> dataList, ViewDishesFragment context) {
        this.dataList = dataList;
        this.context=context;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view_ = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dish_adapter,parent,false);
    return new myViewHolder(view_);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    holder.t10.setText(dataList.get(position).getTitle());
        holder.t11.setText(dataList.get(position).getType());
        Glide.with(this.context).load(dataList.get(position).getImage()).into(holder.R_img);
        };


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t10,t11;
        ImageView R_img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t10=itemView.findViewById(R.id.menu_t7);
            t11=itemView.findViewById(R.id.menu_t8);
            R_img=itemView.findViewById(R.id.menu_img7);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
