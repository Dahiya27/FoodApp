package com.example.android.foodapp.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class cartfragment extends Fragment {

private cartViewModel cartViewModel;
    private FirebaseFirestore fstore;
    private FirebaseAuth fauth;
    private String id;
    private ArrayList<cart> data;
    private cartadapter cartadapter;
    private TextView total;
    Context context;
    RecyclerView rec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        cartViewModel = new ViewModelProvider(this).get(cartViewModel.class);

        View v=inflater.inflate(R.layout.fragment_cart,container,false);

        context=getContext();
        fauth= FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        id=fauth.getCurrentUser().getUid();
        rec=v.findViewById(R.id.recview);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

        data=new ArrayList<>();
        total=v.findViewById(R.id.total);
        fstore.collection("users").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    total.setText(value.getString("total"));
                }
            }
        });

        cartadapter=new cartadapter(data,context);
        fstore.collection("users").document(id).collection("dishes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list) {
                    cart obj = d.toObject(cart.class);
                    data.add(obj);
                }
                rec.setAdapter(cartadapter);

                cartadapter.notifyDataSetChanged();
            }
        } );


        return v;
    }
}