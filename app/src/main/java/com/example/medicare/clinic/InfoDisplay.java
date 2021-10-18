package com.example.medicare.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.medicare.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class InfoDisplay extends AppCompatActivity {

    Button ShowMapButton,submitButton;
    ImageButton backButton;
    RatingBar mRatingBar;
    private String access_code;
    private String address;
    private String contact_number;
    private Long distance;
    private long rating_count;
    private double latitude;
    private double longitude;
    private String name;
    private String opening_hours;
    private String rating;
    private String website;
    private int ratingCount;
    private double newRating;

    TextView clinicName,clinicOH,clinicDist,clinicRat,clinicAdd,clinicCont,clinicWebsite;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://clinicinfo-e71e4-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference ref=database.getReference("clinics");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        name = extras.getString("name");
        address = extras.getString("address");
        distance = extras.getLong("distance");
        website = extras.getString("website");
        opening_hours = extras.getString("openingHours");
        rating = extras.getString("rating");
        contact_number = extras.getString("contactNumber");
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        access_code = extras.getString("accessCode");
        rating_count = extras.getLong("ratingCount");
        String k=Long.toString(rating_count);
        Log.e("newGot",k);
        ratingCount = (int)rating_count;
        String s=Integer.toString(ratingCount);
        Log.e("RatingCount",s);

        clinicName = findViewById(R.id.title_info);
        clinicName.setText(name);
        clinicDist = findViewById(R.id.dist);
        clinicDist.setText(Long.toString(distance) + "km from me");
        clinicOH = findViewById(R.id.open_hour2);
        clinicOH.setText(opening_hours);
        clinicRat = findViewById(R.id.rating);
        String r=rating.substring(0,3);
        clinicRat.setText(r);
        clinicAdd = findViewById(R.id.full_add2);
        clinicAdd.setText(address);
        clinicCont = findViewById(R.id.contact_num2);
        clinicCont.setText(contact_number);
        clinicWebsite = findViewById(R.id.website2);
        clinicWebsite.setText(website);


        ShowMapButton = findViewById(R.id.som);
        ShowMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InfoDisplay.this, ShowOnMapActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

        backButton = findViewById(R.id.backButtonInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mRatingBar=findViewById(R.id.ratingBar);

        submitButton=findViewById(R.id.submit_rating);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRating=mRatingBar.getRating();
                double r=Double.parseDouble(rating);
                newRating=calAve(ratingCount,r,newRating);
                ratingCount=ratingCount+1;
                ref.child(access_code).child("rating_count").setValue(ratingCount);
                String nR=Double.toString(newRating).substring(0,3);
                ref.child(access_code).child("rating").setValue(nR);
                Log.e("Update rating","Update successfully.");
                clinicRat.setText(nR);
            }
        });

    }

    public double calAve(int count, double ave,double newR){

        return (count*ave+newR)/(count+1);

    }

    public String getAveRating(){
        return rating;
    }
    public void updateRating(String accode, double newRat){
        String nR=Double.toString(newRat).substring(0,3);
        ref.child(accode).child("rating").setValue(nR);
    }

    public void displayInfo(){
        clinicName = findViewById(R.id.title_info);
        clinicName.setText(name);
        clinicDist = findViewById(R.id.dist);
        clinicDist.setText(Long.toString(distance) + "km from me");
        clinicOH = findViewById(R.id.open_hour2);
        clinicOH.setText(opening_hours);
        clinicRat = findViewById(R.id.rating);
        String r=rating.substring(0,3);
        clinicRat.setText(r);
        clinicAdd = findViewById(R.id.full_add2);
        clinicAdd.setText(address);
        clinicCont = findViewById(R.id.contact_num2);
        clinicCont.setText(contact_number);
        clinicWebsite = findViewById(R.id.website2);
        clinicWebsite.setText(website);
    }


}
