package com.example.medicare;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoDisplay  extends AppCompatActivity {

    Button ShowMapButton;
    private String access_code;
    private String address;
    private String contact_number;
    private Long distance;
    private double latitude;
    private double longitude;
    private String name;
    private String opening_hours;
    private String rating;
    private String website;

    TextView clinicName,clinicOH,clinicDist,clinicRat,clinicAdd,clinicCont,clinicWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        Intent intent=getIntent();


        Bundle extras=intent.getExtras();
        name=extras.getString("name");
        address=extras.getString("address");
        distance=extras.getLong("distance");
        website=extras.getString("website");
        opening_hours=extras.getString("openingHours");
        rating=extras.getString("rating");
        contact_number=extras.getString("contactNumber");
        latitude=extras.getDouble("latitude");
        longitude=extras.getDouble("longitude");

        clinicName=findViewById(R.id.title_info);
        clinicName.setText(name);
        clinicDist=findViewById(R.id.dist);
        clinicDist.setText(Long.toString(distance)+"km from me");
        clinicOH=findViewById(R.id.open_hour2);
        clinicOH.setText(opening_hours);
        clinicRat=findViewById(R.id.rating);
        clinicRat.setText(rating);
        clinicAdd=findViewById(R.id.full_add2);
        clinicAdd.setText(address);
        clinicCont=findViewById(R.id.contact_num2);
        clinicCont.setText(contact_number);
        clinicWebsite=findViewById(R.id.website2);
        clinicWebsite.setText(website);



        ShowMapButton=findViewById(R.id.som);
        ShowMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(InfoDisplay.this,ShowOnMapActivity.class);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);
            }
        });





    }

}
