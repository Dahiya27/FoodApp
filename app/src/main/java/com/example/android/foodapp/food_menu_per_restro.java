package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import com.example.android.foodapp.ui.home.recycleviewadapter;
import com.example.android.foodapp.ui.home.shops;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class food_menu_per_restro extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_per_restro);
        Intent intent=getIntent();

        mRecyclerView = findViewById(R.id.menu_recylcer);
        mRecyclerView.setHasFixedSize(true);

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