package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

public class activity_splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 4000;

    ImageView logo,img;
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    LottieAnimationView lottieAnimationView;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        logo=findViewById(R.id.logo);
        app_name=findViewById(R.id.app_name);
        img=findViewById(R.id.img);
        fauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        lottieAnimationView=findViewById(R.id.lottie);

        img.animate().translationY(-5500).setDuration(500).setStartDelay(3000);
        logo.animate().translationY(3000).setDuration(500).setStartDelay(3000);
        app_name.animate().translationY(3000).setDuration(500).setStartDelay(3000);
        lottieAnimationView.animate().translationY(3000).setDuration(500).setStartDelay(3000);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity_splashscreen.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);*/
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            fstore.collection("users").document(fauth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        startActivity(new Intent(activity_splashscreen.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                         }
                    else{
                        startActivity(new Intent(activity_splashscreen.this, RestroHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    }
                }
            });
        }
        else{
            startActivity(new Intent(activity_splashscreen.this,StartActivity.class));
        }
    }

}