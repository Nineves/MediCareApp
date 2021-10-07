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
    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        latitude=1.3791271;
        longitude=103.9554448;
        Intent intent=getIntent();
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
