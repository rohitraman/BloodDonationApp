package com.example.darthvader.blooddonation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    FirebaseAuth auth;
    EditText etName,etNumber,etEmail,etBlood,etCity,etState,etPincode,etPass;
    Button btnRegister;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        relativeLayout =  findViewById(R.id.relativeLayout);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        btnRegister = findViewById(R.id.btn_register);
        etName =  findViewById(R.id.et_name1);
        etNumber =  findViewById(R.id.et_mob1);
        etEmail =  findViewById(R.id.et_email1);
        etBlood =  findViewById(R.id.et_blood1);
        etCity =  findViewById(R.id.et_city1);
        etState =  findViewById(R.id.et_state1);
        etPincode =  findViewById(R.id.et_pincode1);
        etPass =  findViewById(R.id.et_pass1);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.hideSoftInputFromWindow(relativeLayout.getWindowToken(),0);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPass.getText().toString();
                final String name = etName.getText().toString();
                final long number = Long.parseLong(etNumber.getText().toString());
                final String bloodGroup = etBlood.getText().toString();
                final String city = etCity.getText().toString();
                final String state = etState.getText().toString();
                final int pincode = Integer.parseInt(etPincode.getText().toString());
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    User user = new User(name,number,email,bloodGroup,city,state,pincode);
                                    reference.child(name).setValue(user);
                                    Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "Sign Up unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
