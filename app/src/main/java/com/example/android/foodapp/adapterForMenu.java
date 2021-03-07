package com.example.android.foodapp;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.foodapp.ui.home.recycleviewadapter;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class adapterForMenu extends RecyclerView.Adapter<adapterForMenu.MyViewHolder> {

    ArrayList<Membr> ar;
    Context context;

    public adapterForMenu(ArrayList<Membr> ar, Context context) {

        this.ar = ar;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // change krna hai imagesingle ko
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagesingle, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ttitl.setText(ar.get(position).getTitle());
        holder.ttype.setText(ar.get(position).getType());

        //String th="thishsh is ashish";
        // holder.ttitl.setText(th);
        //Log.w("ha ha","here  "+ar.get(0).getTitle());
        Glide.with(this.context).load(ar.get(position).getImage()).into(holder.mimg);
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do on click stuff
                Toast.makeText(context, " Ashish is hero! ", Toast.LENGTH_LONG).show();

                Intent intent= new Intent(context, food_menu_per_restro.class );
                // intent.putExtra("id",holder.t1.getText().toString());
                //intent.putExtra()

                intent.putExtra("referRestro", (ar.get(position).second).getPath());
                // Log.w("fuck of ", String.valueOf(v.getVerticalScrollbarPosition()));
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ttitl, ttype;
        public ImageView mimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // id name bdlna hai
            ttitl = (TextView) itemView.findViewById(R.id.tvTitle);
            ttype = (TextView) itemView.findViewById(R.id.tvType);

            mimg = (ImageView) itemView.findViewById(R.id.ivshop);
        }
    }
}