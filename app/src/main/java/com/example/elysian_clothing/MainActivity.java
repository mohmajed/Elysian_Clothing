package com.example.elysian_clothing;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.elysian_clothing.ui.main.SectionsPagerAdapter;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //add button visible for admin with uid "U9QSkY7aI8OzF6rGl3BZIzjOw4X2" admin@gmail 654321
        Button add = (Button) findViewById(R.id.add);
        if (user.getUid().equals("U9QSkY7aI8OzF6rGl3BZIzjOw4X2")) {
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent I = new Intent(MainActivity.this, Add.class);
                    startActivity(I);
                }
            });
        }


    }

    public void buyNow(View view){
        Toast.makeText(MainActivity.this, "Thank You For Using Our Service! :)", Toast.LENGTH_LONG).show();
    }

    public void updt(View view){
        EditText edt= (EditText) findViewById(R.id.newpass);
        String newPass = edt.getText().toString();
        user.updatePassword(newPass);
        Toast.makeText(MainActivity.this, "Password Updated Successfully", Toast.LENGTH_LONG).show();
    }

    public void changePass(View view){
        TextView txt= (TextView) findViewById(R.id.txt);
        txt.setVisibility(View.VISIBLE);
        EditText edt= (EditText) findViewById(R.id.newpass);
        edt.setVisibility(View.VISIBLE);
        Button btn= (Button) findViewById(R.id.Updtpass);
        btn.setVisibility(View.VISIBLE);
    }

    public void log_out(View view){


        FirebaseAuth.getInstance().signOut();

        //updateUI(null);

        Intent I = new Intent(this, login.class);

        startActivity(I);

    }

}