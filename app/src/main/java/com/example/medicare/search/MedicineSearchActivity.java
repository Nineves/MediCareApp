package com.example.medicare.search;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import com.android.volley.RequestQueue;
import com.example.medicare.R;
import com.example.medicare.medicine.MedicineDatabaseController;


import org.json.JSONArray;


public class MedicineSearchActivity extends AppCompatActivity{

    RecyclerView searchResultsRCView;
    MedicineRecyclerAdapter medicineRecyclerAdapter;
    RequestQueue requestQueue;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ImageButton backButton;
    EditText searchBar;
    RecyclerView.LayoutManager layoutManager;
    TextView noResult;

    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    List<String[]> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_activity_search);

        MyThread myThread = new MyThread();
        myThread.start();

        searchResultsRCView = findViewById(R.id.searchResultsRCView);
        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        searchResultsRCView.setLayoutManager(layoutManager);
        medicineRecyclerAdapter = new MedicineRecyclerAdapter(this, searchResults);
        //recyclerAdapter = new RecyclerAdapter(searchResults, false)
        searchResultsRCView.setAdapter(medicineRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        searchResultsRCView.addItemDecoration(dividerItemDecoration);

        noResult = findViewById(R.id.medicineNoResult);

        backButton=findViewById(R.id.backButtonSearchMedicine);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchBar = findViewById(R.id.med_search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noResult.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                medicineRecyclerAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int resultsSize = medicineRecyclerAdapter.getSearchResultsSize();
                if (resultsSize <= 0) {
                    noResult.setVisibility(View.VISIBLE);
                }
                else if (resultsSize > 0) {
                    noResult.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public class MyThread extends Thread {
        String data = "";

        public void run() {
            Log.d("Generic", "My Android Thread is running ...");
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MedicineSearchActivity.this);
                    progressDialog.setMessage("Fetching data...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            if (searchResults.isEmpty()) {
                MedicineDatabaseController.updateMedicineDatabase();
                searchResults = MedicineDatabaseController.getMedicineData();
                medicineRecyclerAdapter.updateSearchResults(searchResults);
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        medicineRecyclerAdapter.notifyDataSetChanged();
                        //Toast.makeText(context, "searchResults size updated: " + recyclerAdapter.getSearchResultsSize(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }
}