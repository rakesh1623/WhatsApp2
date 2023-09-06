package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String userName , about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,home.class));
            }
        });


        database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users = snapshot.getValue(Users.class);
                                Picasso.get().load(users.getProfilePic())
                                        .placeholder(R.drawable.user_naya)
                                        .into(binding.circleImageView);



                                    binding.userName.setText(users.getUserName().toString());
                                    binding.about.setText(users.getAbout().toString());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }});

        binding.userNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.userName.setEnabled(true);
                Toast.makeText(ProfileActivity.this, "Now you can edit your username", Toast.LENGTH_SHORT).show();
            }
        });
        binding.aboutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.about.setEnabled(true);
                Toast.makeText(ProfileActivity.this, "Now you can edit your about", Toast.LENGTH_SHORT).show();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.userName.setEnabled(false);
                binding.about.setEnabled(false);
                userName = binding.userName.getText().toString();
                about = binding.about.getText().toString();



                HashMap<String , Object> obj = new HashMap<>();
                obj.put("userName" , userName);
                obj.put("about",about);

                database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);

                binding.userName.setText(userName);
                binding.about.setText(about);


            }
        });




        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , 25);
            }
        });


        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            Uri uri =  data.getData();
            binding.circleImageView.setImageURI(uri);
            Picasso.get().load(uri).into(binding.circleImageView);

            final StorageReference reference = storage.getReference().child("PROFILE_PIC")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilePic").setValue(uri.toString());
                        }
                    });

                }
            });
        }
    }
}