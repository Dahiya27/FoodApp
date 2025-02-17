package com.example.android.foodapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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

import org.w3c.dom.Document;

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
//userPhotoRef.document().collection("");
        mRecyclerView = findViewById(R.id.recycl);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //Ashish path firebase database

        userPhotoRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<Pair<Membr,DocumentReference>>list = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        //Log.w(document.getId(),"  Real Id se aa zukku ! ");



                        if(document.exists())
                        {
                            DocumentReference menu_child = document.getReference();
                            Membr photo=document.toObject(Membr.class);

                           // Log.w("type","  "+photo.getType()+" I did this "+id+" "+id.equals(photo.getType()));

                        boolean f;//=id.equals(photo.getType());
                     f=id.charAt(2)==photo.getType().toString().charAt(2);
                           if(f) { list.add(  new Pair<Membr,DocumentReference>(photo,menu_child)  );}
                        }
                        // Do what you need to do with your list
//                       Log.w("bro"," "+list.get(0).getTitle()+"   "+list.get(0).getImage());
                        mAdapter= new recycleviewadapter(list,shops.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
        });



    }
}