package com.example.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class mainPage extends AppCompatActivity {

    ImageView profilebtn;
    ImageView reminderbtn;
    ImageView infobtn;
    ImageView searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        profilebtn = findViewById(R.id.profilebutton);
        reminderbtn = findViewById(R.id.reminderbutton);
        infobtn = findViewById(R.id.infobutton);
        searchbtn = findViewById(R.id.searchclinicbutton);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profile.class));
            }
        });

        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), reminder.class));
            }
        });

        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), info.class));
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), search.class));
            }
        });
    }
}