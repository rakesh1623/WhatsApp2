package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.databinding.ActivityChatDetailsAcitivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatDetailsAcitivity extends AppCompatActivity {

    ActivityChatDetailsAcitivityBinding binding;
    FirebaseAuth mAuth ;
    FirebaseDatabase database;

    public static final int NOTIFICATION_ID = 100;
    public static final String CHANNEL_ID = "My Channel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final String senderId = mAuth.getUid();

        String recieveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userProfilePic = getIntent().getStringExtra("userProfilePic");


        Drawable drawable = ResourcesCompat.getDrawable(getResources() , R.drawable.chaticon,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.chaticon)
                        .setContentText("NEW MESSAGE")
                                .setSubText("new message from CHAT APP")
                                        .setChannelId(CHANNEL_ID)
                                                .build();

        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"NEW CHANNEL",NotificationManager.IMPORTANCE_HIGH));





        binding.chatUserName.setText(userName);
        Picasso.get().load(userProfilePic).placeholder(R.drawable.user_naya).into(binding.chatProfilePic);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chatDetailsAcitivity.this , home.class));
            }
        });


        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels , this, recieveId);

        binding.chatUserRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.chatUserRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

        database.getReference().child("CHATS").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    model.setMessageId(dataSnapshot.getKey());
                    messageModels.add(model);
                }

                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.msgSend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotificationPermission")
            @Override
            public void onClick(View view) {
                String mesage = binding.enterMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId , mesage);
                model.setTimmeStamp(new Date().getTime());
                binding.enterMessage.setText("");

                nm.notify(NOTIFICATION_ID,notification);



                database.getReference().child("CHATS").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("CHATS")
                                .child(receiverRoom).push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                    }
                });
            }
        });
    }
}