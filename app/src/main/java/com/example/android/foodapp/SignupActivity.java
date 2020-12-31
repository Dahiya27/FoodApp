package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Spinner spinner=(Spinner) findViewById(R.id.spinner);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        EditText name=(EditText)findViewById(R.id.name);
        EditText emailid = (EditText) findViewById(R.id.emailid);
        EditText password = (EditText) findViewById(R.id.password);
        EditText cpassword=(EditText) findViewById(R.id.cpassword);
        Button signup_button = (Button) findViewById(R.id.signup_button);
        TextView loginbtn = (TextView)findViewById(R.id.direct);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_emailid = emailid.getText().toString();
                String txt_password = password.getText().toString();
                String Name=name.getText().toString();
                String txt_cpassword=cpassword.getText().toString();
                if(TextUtils.isEmpty(Name)) {
                    name.setError("Empty your name ");
                    name.requestFocus();
                }
                else if(TextUtils.isEmpty(txt_emailid)) {
                    emailid.setError("Enter EmailId");
                    emailid.requestFocus();
                }
                else if(TextUtils.isEmpty(txt_password)) {
                    password.setError("Enter password");
                    password.requestFocus();
                }
                else if(txt_password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Password Too Short!", Toast.LENGTH_SHORT).show();
                }else if(!txt_password.equals(txt_cpassword)) {
                    cpassword.setError("Password did not Match");
                    cpassword.requestFocus();
                }
                else {
                    registerUser(txt_emailid, txt_password,Name);
                }
            }

        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String emailid,  String password,String Name){
        mAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "congratulations "+Name+" .Your Signup is successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignupActivity.this, "Sorry "+Name+". this Email is already used!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text+"(Saved)",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}