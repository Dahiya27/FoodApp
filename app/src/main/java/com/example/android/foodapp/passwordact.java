package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordact extends AppCompatActivity {


    private EditText passwEmail ;
    private Button resetp;
    private FirebaseAuth AUTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordact);
        passwEmail=(EditText)findViewById(R.id.etpassemail);
        resetp=(Button)findViewById(R.id.resetpass);
        AUTH=FirebaseAuth.getInstance();
        resetp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String s=passwEmail.getText().toString().trim();
             if(s.equals(""))
             {
                 passwEmail.setError("Please enter your Email address ");
             }
             else
             {
                 AUTH.sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(passwordact.this,"Email sent !",Toast.LENGTH_SHORT).show();
                             finish();
                             startActivity( new Intent(passwordact.this, LoginActivity.class));

                         }
                         else
                         {
                             Toast.makeText(passwordact.this,"Error in sending email\n Try again",Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
            }
        });
    }
}