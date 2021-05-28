package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth fauth=FirebaseAuth.getInstance();
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, SignupActivity.class));
        });
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });

        Button restro = (Button) findViewById(R.id.restro);
        restro.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, RestroActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            fstore.collection("users/"+fauth.getCurrentUser().getUid()+"/dishes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        fstore.collection("users/"+fauth.getCurrentUser().getUid()+"/dishes").document(snapshot.getId()).delete();
                    }
                }
            });

fstore.collection("users").document(fauth.getCurrentUser().getUid()).update("total","0");
            }
    }

}