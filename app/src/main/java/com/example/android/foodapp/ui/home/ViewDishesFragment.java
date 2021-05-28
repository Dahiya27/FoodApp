package com.example.android.foodapp.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.foodapp.Membr;
import com.example.android.foodapp.R;
import com.example.android.foodapp.ViewDishAdapter;
import com.example.android.foodapp.Membr;
import com.example.android.foodapp.adapterForMenu;
import com.example.android.foodapp.food_menu_per_restro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewDishesFragment extends Fragment {


    private ViewDishesViewModel ViewDishesModel;
    ViewDishAdapter adapter;
    RecyclerView viewDishRecView;
    FirebaseFirestore db;
    ArrayList<Membr> list = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ViewDishesModel = new ViewModelProvider(this).get(ViewDishesViewModel.class);
        View root = inflater.inflate(R.layout.view_dishes_fragment, container, false);
        setHasOptionsMenu(true);




        viewDishRecView =(RecyclerView)root.findViewById(R.id.recView2);
        viewDishRecView.setLayoutManager(new LinearLayoutManager(getActivity()));


        viewDishRecView.setAdapter(adapter);
        db =FirebaseFirestore.getInstance();


        CollectionReference userRef = db.collection("Restaurants").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("foodmenu");


        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        if(document.exists())
                        {

                            Membr photo=document.toObject(Membr.class);


                            { list.add( photo );}
                        }

                        adapter= new ViewDishAdapter(list, ViewDishesFragment.this);
                        viewDishRecView.setAdapter(adapter);
                    }
                }
            }
        });
        return root;



    }

}