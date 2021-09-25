package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView ourMR;
    MRAdapter mrAdapter;
    ArrayList<MedicineReminder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();//hides the action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //working with data
        ourMR = findViewById(R.id.ourMR);
        ourMR.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MedicineReminder>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Medicine Reminder");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MedicineReminder p = dataSnapshot1.getValue(MedicineReminder.class);
                    list.add(p);
                }
                mrAdapter = new MRAdapter(MainActivity.this, list);
                ourMR.setAdapter(mrAdapter);
                mrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //set code to show an error
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, CreateMr.class);
        startActivity(intent);
    }
}