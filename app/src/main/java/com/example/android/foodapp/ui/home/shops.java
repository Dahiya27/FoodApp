package com.example.android.foodapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodapp.Membr;
import com.example.android.foodapp.R;
//import com.example.android.foodapp.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class shops extends AppCompatActivity {
///hhh
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    CollectionReference userPhotoRef = rootRef.collection("Restaurants");

    //private  final Firestore db;
    //CollectionReference cities = db.collection("data");

    //CollectionReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        mRecyclerView = findViewById(R.id.recycl);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();


        //Ashish path firebase database

        userPhotoRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Membr>list = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        if(document.exists())
                        {
                            Membr photo=document.toObject(Membr.class);
                            list.add(photo);
                        }
                        // Do what you need to do with your list
                       Log.w("bro"," "+list.get(0).getTitle()+"   "+list.get(0).getImage());
                        mAdapter= new recycleviewadapter(list,shops.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
        });



    }
}