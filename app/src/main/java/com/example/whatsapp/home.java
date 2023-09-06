package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#009688"));
        actionBar.setBackgroundDrawable(colorDrawable);

        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);




        viewPageAdapter adapter = new viewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuLogout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(home.this , MainActivity.class));

        }
        if(item.getItemId() == R.id.menuProfile){
            startActivity(new Intent(home.this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}