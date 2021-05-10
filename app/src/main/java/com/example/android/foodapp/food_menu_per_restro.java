package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.foodapp.ui.home.cartfragment;
import com.example.android.foodapp.ui.home.viewcart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class food_menu_per_restro extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Button ViewCart;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_per_restro);
        Intent intent=getIntent();
ViewCart=findViewById(R.id.viewcartbutton);
        mRecyclerView = findViewById(R.id.menu_recylcer);
        mRecyclerView.setHasFixedSize(true);
ViewCart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       startActivity(new Intent(food_menu_per_restro.this,viewcart.class));
    }
});
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String refer = intent.getStringExtra("referRestro");

        CollectionReference userRef = db.document(refer).collection("foodmenu");

       userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful())
               {
                   ArrayList<Membr> list = new ArrayList<>();
                   for(QueryDocumentSnapshot document : task.getResult())
                   {
                       if(document.exists())
                       {

                           Membr photo=document.toObject(Membr.class);


                           { list.add( photo );}
                       }

                       // yha  adapter bnana hai and naam bdlna hai
                       mAdapter= new adapterForMenu(list,food_menu_per_restro.this);
                       mRecyclerView.setAdapter(mAdapter);
                   }
               }
           }
        });



    }
}