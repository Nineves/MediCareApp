package com.example.medicare.medicine_search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.medicare.R;
import com.example.medicare.medicine.MedicineDatabaseController;


public class MedicineSearchActivity extends AppCompatActivity{

    RecyclerView searchResultsRCView;
    MedicineRecyclerAdapter medicineRecyclerAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ImageButton backButton;
    EditText searchBar;
    List<String[]> searchResults = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    //Context context = this;


    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    // for clinic search result, each element in list has format
    // {"name", "clinic ave rating", "clinic distance", "clinic address"}


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
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                medicineRecyclerAdapter.getFilter().filter(charSequence);
                //search=charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

    /*
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
    */



}