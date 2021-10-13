package com.example.medicare.search;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import com.android.volley.RequestQueue;
import com.example.medicare.R;
import com.example.medicare.medicine.MedicineDatabaseController;

import java.net.*;
import java.io.*;


import org.json.JSONObject;
import org.json.JSONArray;


public class SearchActivity extends AppCompatActivity{

    RecyclerView searchResultsRCView;
    RecyclerAdapter recyclerAdapter;
    RequestQueue requestQueue;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    String medicineURLstring = "https://data.gov.sg/api/action/datastore_search?resource_id=3ee20559-372d-42f0-bde9-245e21f7f39b&limit=5621";
    String medicineStr;
    JSONArray medicineRecordsjson;
    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    // for clinic search result, each element in list has format
    // {"name", "clinic ave rating", "clinic distance", "clinic address"}
    List<String[]> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MyThread myThread = new MyThread();
        myThread.start();


        searchResultsRCView = findViewById(R.id.searchResultsRCView);
        recyclerAdapter = new RecyclerAdapter(searchResults, true);
        //recyclerAdapter = new RecyclerAdapter(searchResults, false);

        searchResultsRCView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        searchResultsRCView.addItemDecoration(dividerItemDecoration);

    }

    public class MyThread extends Thread {
        String data = "";

        public void run() {
            Log.d("Generic", "My Android Thread is running ...");
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(SearchActivity.this);
                    progressDialog.setMessage("Fetching data...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            MedicineDatabaseController.updateMedicineDatabase();
            searchResults = MedicineDatabaseController.getMedicineData();

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        recyclerAdapter.notifyDataSetChanged();
                    }

                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}