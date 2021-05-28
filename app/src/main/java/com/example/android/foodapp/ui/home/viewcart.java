package com.example.android.foodapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.android.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class viewcart extends AppCompatActivity {

    private FirebaseFirestore fstore;
    private FirebaseAuth fauth;
    private String id;
    private ArrayList<cart> data;
    private cartadapter cartadapter;
    private TextView total;
    Context context;
RecyclerView rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcart);

        context=getParent();
        fauth= FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        id=fauth.getCurrentUser().getUid();
         rec=findViewById(R.id.recview);
        rec.setLayoutManager(new LinearLayoutManager(this));

        data=new ArrayList<>();
        total=findViewById(R.id.total);
        fstore.collection("users").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                assert value != null;
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


    }
}