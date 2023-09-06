package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            if(mAuth.getCurrentUser().isEmailVerified())
                startActivity(new Intent(MainActivity.this , home.class));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        binding.createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,registerUser.class));

            }
        });

        binding.userLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signInWithEmailAndPassword(binding.userEmail.getText().toString() , binding.userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(MainActivity.this, home.class));

                            }
                            else{
                                Toast.makeText(MainActivity.this, "PLEASE VERIFY YOUR EMAIL", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

            }
        });

    }
}