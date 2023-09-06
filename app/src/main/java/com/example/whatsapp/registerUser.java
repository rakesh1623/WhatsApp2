package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivityRegisterUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerUser extends AppCompatActivity {

    ActivityRegisterUserBinding binding;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

//    Long

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.userRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(binding.userRegisterEmail.getText().toString(), binding.userRegisterPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Users users = new Users(binding.userRegisterName.getText().toString() , binding.userRegisterEmail.getText().toString(), binding.userRegisterPass.getText().toString(),"",000000000000L);
                            String uid = task.getResult().getUser().getUid();
                            database.getReference().child("USERS").child(uid).setValue(users);

                            mAuth.getCurrentUser().sendEmailVerification();

                            startActivity(new Intent(registerUser.this , MainActivity.class));


                            Toast.makeText(registerUser.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(registerUser.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}