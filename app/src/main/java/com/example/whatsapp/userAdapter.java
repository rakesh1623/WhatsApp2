package com.example.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {


    ArrayList<Users> list;
    Context context;




    public userAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.users_chat_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        //users.setUserTime(000000000000L);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.user_naya).into(holder.image);
        holder.userName.setText(users.getUserName());






        FirebaseDatabase.getInstance().getReference().child("CHATS")
                        .child(FirebaseAuth.getInstance().getUid()+users.getUserId())
                                .orderByChild("timmeStamp")
                                        .limitToLast(1)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.hasChildren()){
                                                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                                                holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                                                                Date date = new Date( (snapshot1.child("timmeStamp").getValue(Long.class)));

                                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY- h:mm a");
                                                                String strDate = simpleDateFormat.format(date);
                                                                holder.userTime.setText(strDate);


                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , chatDetailsAcitivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("userProfilePic", users.getProfilePic());
                intent.putExtra("userName" , users.getUserName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView userName , lastMessage , userTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.chatProfilePic);
            userName = itemView.findViewById(R.id.chatUserName);
            lastMessage = itemView.findViewById(R.id.chatLastMessage);
            userTime = itemView.findViewById(R.id.userTime);

        }
    }
}
