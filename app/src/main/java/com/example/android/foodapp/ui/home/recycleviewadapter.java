package com.example.android.foodapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.android.foodapp.Membr;
import com.example.android.foodapp.R;
import com.example.android.foodapp.food_menu_per_restro;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class recycleviewadapter extends RecyclerView.Adapter<recycleviewadapter.MyViewHolder> {

    ArrayList<Pair<Membr,DocumentReference>> ar;
    Context context;
    //ArrayList<Membr>b;
    public recycleviewadapter(ArrayList<Pair<Membr, DocumentReference>> ar, Context context) {



       /* for (Pair <Membr,DocumentReference> temp : arr)
        {
   b.add(temp.first);
        }
*/
        this.ar = ar;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagesingle,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
      //  Log.w("hu hu","here");
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ttitl.setText(ar.get(position).first.getTitle());
        holder.ttype.setText(ar.get(position).first.getType());

        //String th="thishsh is ashish";
     // holder.ttitl.setText(th);
        //Log.w("ha ha","here  "+ar.get(0).getTitle());
        Glide.with(this.context).load(ar.get(position).first.getImage()).into(holder.mimg);
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
        });

    }

    @Override
    public int getItemCount() {
        return ar.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView ttitl,ttype;
       public ImageView mimg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             ttitl= (TextView) itemView.findViewById(R.id.tvTitle);
             ttype= (TextView) itemView.findViewById(R.id.tvType);
             //Log.w("one more","  "+ttitl.setText("assh"));
          //  ttitl.setText("check code in recyleviewapdapter.java..");

             mimg= (ImageView) itemView.findViewById(R.id.ivshop);
            //Glide.with(context).load(ar.get(0).getImage()).into(mimg);
        }
    }


}
