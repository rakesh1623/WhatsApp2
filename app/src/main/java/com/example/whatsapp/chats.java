package com.example.whatsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatsapp.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class chats extends Fragment {


    public chats() {
        // Required empty public constructor
    }


    FragmentChatsBinding binding;


    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding  = FragmentChatsBinding.inflate(inflater , container , false);
        database = FirebaseDatabase.getInstance();
        userAdapter adapter = new userAdapter(list , getContext());
        binding.chatRecyclerView.setAdapter(adapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);


        database.getReference().child("USERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }
                }
//
//                Collections.sort(list, new Comparator<Users>() {
//                    @Override
//                    public int compare(Users users, Users t1) {
//                       return users.userTime.compareTo(t1.userTime);
//                    }
//                });
//                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}