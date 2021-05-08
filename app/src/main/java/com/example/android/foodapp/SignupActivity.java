package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;

    ImageView Show;
    ImageView Hide;
    EditText password;
    EditText cpassword;
    TextView loginbtn;
    int score = 0;
    boolean upper = false;
    boolean lower = false;
    boolean digit = false;
    boolean specialChar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Spinner spinner=(Spinner) findViewById(R.id.spinner);
Spinner Gender=(Spinner)findViewById(R.id.gender);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Gender.setAdapter(adapter1);
        Gender.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        EditText name=(EditText)findViewById(R.id.name);
        EditText emailid = (EditText) findViewById(R.id.emailid);
        fstore=FirebaseFirestore.getInstance();
         password =  findViewById(R.id.password);
         cpassword= findViewById(R.id.cpassword);
        Button signup_button = (Button) findViewById(R.id.signup_button);
        TextView loginbtn = (TextView)findViewById(R.id.direct);



        Show=findViewById(R.id.show);
        Hide=findViewById(R.id.hide);
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    Show.setImageResource(R.drawable.hideeye);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                } else {
                    Show.setImageResource(R.drawable.showeye);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                }
            }
        });
        Hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    Hide.setImageResource(R.drawable.hideeye);
                    cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cpassword.setSelection(cpassword.getText().length());
                } else {
                    Hide.setImageResource(R.drawable.showeye);
                    cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cpassword.setSelection(cpassword.getText().length());
                }
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_emailid = emailid.getText().toString();
                String txt_password = password.getText().toString();
                String Name=name.getText().toString();
                int State=spinner.getSelectedItemPosition();
                String state=spinner.getSelectedItem().toString();
                String pGender=Gender.getSelectedItem().toString();
                String txt_cpassword=cpassword.getText().toString();

                int gender=Gender.getSelectedItemPosition();
                for (int i = 0; i < txt_password.length(); i++) {
                    char c = txt_password.charAt(i);

                    if (!specialChar && !Character.isLetterOrDigit(c)) {
                        score++;
                        specialChar = true;
                    } else {
                        if (!digit && Character.isDigit(c)) {
                            score++;
                            digit = true;
                        } else {
                            if (!upper || !lower) {
                                if (Character.isUpperCase(c)) {
                                    upper = true;
                                } else {
                                    lower = true;
                                }

                                if (upper && lower) {
                                    score++;
                                }
                            }
                        }
                    }
                }

                if(TextUtils.isEmpty(txt_emailid) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(SignupActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else if(txt_password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Password Too Short!", Toast.LENGTH_SHORT).show();
                }else if(!txt_password.equals(txt_cpassword)) {
                    Toast.makeText(SignupActivity.this,"Passwords do not match!",Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(txt_emailid).matches()){
                    Toast.makeText(SignupActivity.this,"Please provide a valid email!",Toast.LENGTH_SHORT).show();
                }
                else if (score <= 1 && txt_password.length() > 6) {
                    Toast.makeText(getApplicationContext(), "password is weak try another,add uppercase or special character", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.equals(txt_cpassword)){
                    if (score == 2) {
                        Toast.makeText(getApplicationContext(), "Medium password", Toast.LENGTH_SHORT).show();
                        registerUser(txt_emailid, txt_password, Name,state,State,gender,pGender);

                    } else if (score == 3) {
                        Toast.makeText(getApplicationContext(), "Strong password", Toast.LENGTH_SHORT).show();
                        registerUser(txt_emailid, txt_password, Name,state,State,gender,pGender);

                    }
                    else if(score==4){
                        Toast.makeText(getApplicationContext(), "Very Strong password", Toast.LENGTH_SHORT).show();
                        registerUser(txt_emailid, txt_password, Name,state,State,gender,pGender);

                    }
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

    private void registerUser(String emailid,  String password, String Name, String  State,int state,int gender,String Gender){
        mAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(Name, emailid, State);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Congratulations "+Name+" .Your Signup is successful!", Toast.LENGTH_SHORT).show();
                                addUser(Name,emailid,state,gender,State,Gender);

                            }
                            else {
                                Toast.makeText(SignupActivity.this, "Failed to register! Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this, "Failed to register! Please try again!", Toast.LENGTH_SHORT).show();
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
    private void addUser(String name,String email,int state,int gender,String State,String Gender){
String id=mAuth.getCurrentUser().getUid().toString();
Map<String,Object> doc = new HashMap<>();
doc.put("Id",id);
doc.put("FullName",name);
doc.put("Email",email);
doc.put("State",state);
doc.put("gender",gender);
doc.put("stateName",State);
doc.put("genderType",Gender);
doc.put("Description","noDescription");
doc.put("image","noImage");
doc.put("Address","noAddress");

fstore.collection("users").document(id).set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void avoid) {
startActivity(new Intent(getApplicationContext(),MainActivity.class));
finish();
    }
});

    }

}