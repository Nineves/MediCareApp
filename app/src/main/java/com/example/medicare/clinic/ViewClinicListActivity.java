package com.example.medicare.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicare.R;

import java.util.ArrayList;
import java.util.List;

public class ViewClinicListActivity extends AppCompatActivity {

    RecyclerView clinicRecycler;
    RecyclerviewAdapter recyclerviewAdapter;
    EditText searchView;
    CharSequence search="";
    ClinicDatabaseController controller;
    ImageButton backButton;

    List<Clinic> searchResults = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i=getIntent();
        setContentView(R.layout.clinic_search_result);
        backButton=findViewById(R.id.backButtonSearch);
        searchView=findViewById(R.id.search_bar);
        controller = new ClinicDatabaseController();
        controller.readDataFromClinic(getApplicationContext(), new ClinicDatabaseController.FirebaseSuccessListener() {
            @Override
            public void onDataCompleted(boolean isDataCompleted) {
                Log.d("viewList", "DATA ALREADY LOADED");
            }
        });
        searchResults=controller.getClinicArrayList();

        displayResults(searchResults);

        searchDatabase();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setClinicRecycler(List<Clinic> clinicDatalist){

        clinicRecycler=findViewById(R.id.clinicSearchRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        clinicRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter = new RecyclerviewAdapter(this,clinicDatalist);
        clinicRecycler.setAdapter(recyclerviewAdapter);

    }

    public void searchDatabase(){
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                recyclerviewAdapter.getFilter().filter(charSequence);
                search=charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void displayResults(List<Clinic> results){
        setClinicRecycler(results);
    }



}
