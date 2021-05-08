package com.example.android.foodapp.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.foodapp.R;
import com.example.android.foodapp.StartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

import io.grpc.internal.ClientStreamListener;

public class cartadapter extends RecyclerView.Adapter<cartadapter.cartViewHolder> {
    @NonNull
    @NotNull

    private ArrayList<cart>data;
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    FirebaseAuth fauth=FirebaseAuth.getInstance();
    Context context;
    ProgressDialog progressDialog;
    float y;
    public cartadapter(ArrayList<cart> data,Context context){
       this.data=data;
       this.context=context;
    }
    public cartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context= parent.getContext();
       LayoutInflater inflater=LayoutInflater.from(parent.getContext());
       View v=inflater.inflate(R.layout.cartitems,parent,false);
        return new cartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull cartViewHolder holder, int position) {
holder.t1.setText(data.get(position).getDishname());
holder.t2.setText(data.get(position).getPrice());

holder.t3.setText(data.get(position).getQuantity());
        y=grandTotal(data);
        fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","₹"+String.valueOf(y));

        String p=data.get(position).getPrice();
        p=p.substring(1);
        float z=Float.parseFloat(p);
        holder.dishtotal.setText("₹"+String.valueOf(z));
        holder.plus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int q=Integer.parseInt((String)holder.t3.getText().toString());
        String price=holder.t2.getText().toString();
        price=price.substring(1);
        float p=Float.parseFloat(price);
        float total=p;
        q=q+1;
        total=p*q;
        holder.dishtotal.setText("₹"+String.valueOf(total));
        holder.t3.setText(String.valueOf(q));

y+=p;
        fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","₹"+String.valueOf(y));

    }
});
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q=Integer.parseInt(holder.t3.getText().toString());
                String price=holder.t2.getText().toString();
                price=price.substring(1);
                float p=Float.parseFloat(price);
                float total=p*q;
                if(q>1){

                q=q-1;
                total=p*q;
                    y-=p;
                }
                holder.t3.setText(String.valueOf(q));
                holder.dishtotal.setText("₹"+String.valueOf(total));


                fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","₹"+String.valueOf(y));

            }
        });
holder.remove.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        progressDialog=new ProgressDialog(context);
String e=holder.t2.getText().toString();
int q=Integer.parseInt(holder.t3.getText().toString());
e=e.substring(1);
float u=Float.parseFloat(e)*q;
        y-=u;

        progressDialog.setMessage("removing from cart"); // Setting Message
        progressDialog.setTitle("Enjoy the delicious food"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,data.size());


        fstore.collection("users").document(fauth.getCurrentUser().getUid()).collection("dishes").document(holder.t1.getText().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();


                fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","₹"+String.valueOf(y));


                }
        });
    }
});



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class cartViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,dishtotal;
        Button plus,minus,remove;
        public cartViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t11);
            t2=itemView.findViewById(R.id.t12);
            t3=itemView.findViewById(R.id.t133);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            dishtotal=itemView.findViewById(R.id.dishtotal);
            remove=itemView.findViewById(R.id.remove);
                   }
    }
    private float grandTotal(ArrayList<cart> items){

        float totalPrice = 0;
        for(int i = 0 ; i < items.size(); i++) {
            String z=items.get(i).getPrice();
            int q=Integer.parseInt(items.get(i).getQuantity());
            z=z.substring(1);
            float p= Float.parseFloat(z);
            totalPrice += p;
        }

        return totalPrice;
    }
}
