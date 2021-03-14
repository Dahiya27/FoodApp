package com.example.android.foodapp;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    //int quantity=0;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // change krna hai imagesingle ko
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_tiles, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ttitl.setText(ar.get(position).getTitle());
        holder.ttype.setText(ar.get(position).getType());

        holder.pb.setOnClickListener(v -> {
            int q=Integer.parseInt((String) holder.vval.getText());
            q=q+1;
            holder.vval.setText(String.valueOf(q));
        });
        holder.mb.setOnClickListener(v -> {
            int q=Integer.parseInt((String) holder.vval.getText());
            if(q>0) q=q-1;
            holder.vval.setText(String.valueOf(q));
        });

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ttitl, ttype, vval;
        public ImageView mimg;
        public Button pb,mb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // id name bdlna hai
            vval = (TextView) itemView.findViewById(R.id.quantity_text_view);
            ttitl = (TextView) itemView.findViewById(R.id.menu_t1);
            ttype = (TextView) itemView.findViewById(R.id.menu_t2);
            mimg = (ImageView) itemView.findViewById(R.id.menu_img1);
            pb = (Button) itemView.findViewById(R.id.plus);
            mb = (Button) itemView.findViewById(R.id.minus);
//            itemView.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}