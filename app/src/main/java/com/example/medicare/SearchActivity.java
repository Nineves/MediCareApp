package com.example.medicare;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//import java.net.HttpURLConnection;
//import java.net.URL;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

import java.net.*;
import java.io.*;


//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import org.json.JSONObject;
import org.json.JSONArray;
//import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


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

        //dummy medicine data
        /*
        for (int i = 1; i <= 20; i++ ) {
            String[] medicine = {"Medicine " + i, "Dosage", "Ingredients List", "Manufacturer " + Math.random()};
            searchResults.add(medicine);
        }
        */
        /*
        //dummy clinic data
        for (int i = 1; i <= 20; i++ ) {
            String[] clinic = {"Clinic " + i, "Rating", "Distance", "Address " + Math.random()};
            searchResults.add(clinic);
        }
        */


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

            try {
                //obtaining medicine records from api
                /*
                URL url = new URL(medicineURLstring);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }
                medicineStr = buffer.toString();
                */

                //Document doc = Jsoup.connect(medicineURLstring).ignoreContentType(true).get();
                //medicineStr = doc.text();

                //this one works
                //medicineStr = readUrl(medicineURLstring);
                //JSONObject jsonObject = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");

                //Gson gson = new Gson();
                //this is the fucked line
                //medicineRecordsjson = gson.fromJson(medicineStr, JsonArray.class);


                URL url = new URL(medicineURLstring);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }
                if (!data.isEmpty()) {
                    medicineStr = data;
                    JSONObject jsonObjectMed = new JSONObject(medicineStr);
                    JSONObject jsonObjectResults = jsonObjectMed.getJSONObject("result");

                    medicineRecordsjson = jsonObjectResults.getJSONArray("records");
                    searchResults.clear();
                    for (int i = 0; i < medicineRecordsjson.length(); i++)
                    {


                        JSONObject medObj = medicineRecordsjson.getJSONObject(i);
                        String medicineName = cleanString(medObj.getString("product_name"));
                        String dosageForm = cleanString(medObj.getString("dosage_form"));
                        String ingredients = cleanString(medObj.getString("active_ingredients"));
                        String manufacturer = cleanString(medObj.getString("license_holder"));

                        String[] medicine = {medicineName, dosageForm, ingredients, manufacturer};
                        searchResults.add(medicine);

                    }

                    //Toast.makeText(getApplicationContext(), searchResults.get(0)[0], Toast.LENGTH_LONG);
                }
                else {
                    //Toast.makeText(getApplicationContext(), "EMPTY", Toast.LENGTH_LONG);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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

    //cleans string from api
    public String cleanString(String originalStr) {
        String newStr = originalStr;
        if (!(Character.isLetter(originalStr.charAt(0)) || Character.isDigit(originalStr.charAt(0)))){
            newStr = newStr.substring(1, originalStr.length()-1);
        }
        if (!(Character.isLetter(originalStr.charAt(originalStr.length()-1)) || Character.isDigit(originalStr.charAt(originalStr.length()-1)))){
            newStr = newStr.substring(0, originalStr.length()-2);
        }
        return newStr;
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

    /*
    public void fetchData(String ) {
        JsonObjectRequest jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiURL, null, )
    }
     */


}