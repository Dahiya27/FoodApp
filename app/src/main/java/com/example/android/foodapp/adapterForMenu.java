package com.example.android.foodapp;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterForMenu extends RecyclerView.Adapter<adapterForMenu.MyViewHolder> {

    ArrayList<Membr> ar;
    private FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    private FirebaseAuth fauth=FirebaseAuth.getInstance();

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


holder.addtocart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Map<String,Object>a=new HashMap<>();
        a.put("dishname",holder.ttitl.getText().toString());
        a.put("price",holder.ttype.getText().toString());
        a.put("quantity","1");
        fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","0");

        fstore.collection("users").document(fauth.getCurrentUser().getUid()).collection("dishes").document(holder.ttitl.getText().toString()).set(a).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG ).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }
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
        public Button pb,mb,addtocart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // id name bdlna hai
            ttitl = (TextView) itemView.findViewById(R.id.menu_t1);
            ttype = (TextView) itemView.findViewById(R.id.menu_t2);
            mimg = (ImageView) itemView.findViewById(R.id.menu_img1);
            addtocart=itemView.findViewById(R.id.add_to_cart_button);
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