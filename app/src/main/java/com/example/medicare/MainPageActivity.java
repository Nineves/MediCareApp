package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicare.clinic.MapsActivity;
import com.example.medicare.medicineReminder.MedicineActivity;
import com.example.medicare.search.MedicineSearchActivity;
import com.example.medicare.user.UserProfileActivity;

public class MainPageActivity extends AppCompatActivity {

    ImageView profilebtn;
    ImageView reminderbtn;
    ImageView medicinebtn;
    ImageView clinicbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        profilebtn = findViewById(R.id.profile_button_main);
        reminderbtn = findViewById(R.id.reminderbutton);
        medicinebtn = findViewById(R.id.infobutton);
        clinicbtn = findViewById(R.id.searchclinicbutton);


        clinicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        medicinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MedicineSearchActivity.class));
            }
        });

        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MedicineActivity.class));
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            }
        });
    }
}
