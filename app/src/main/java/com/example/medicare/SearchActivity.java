package com.example.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/*
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 */

public class SearchActivity extends AppCompatActivity {

    RecyclerView searchResultsRCView;
    RecyclerAdapter recyclerAdapter;
    //RequestQueue requestQueue;
    //String apiURL = "https://data.gov.sg/api/action/datastore_search";

    // for medicine search result, each element in list has format
    // {"name", "dosage form", "ingredients", "manufacturers"}
    // for clinic search result, each element in list has format
    // {"name", "clinic ave rating", "clinic distance", "clinic address"}
    List<String[]> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //dummy medicine data
        for (int i = 1; i <= 20; i++ ) {
            String[] medicine = {"Medicine " + i, "Dosage", "Ingredients List", "Manufacturer " + i};
            searchResults.add(medicine);
        }

        searchResultsRCView = findViewById(R.id.searchResultsRCView);
        recyclerAdapter = new RecyclerAdapter(searchResults, true);

        searchResultsRCView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        searchResultsRCView.addItemDecoration(dividerItemDecoration);

        /*
        try {
            URL url = new URL("https://data.gov.sg/api/action/datastore_search");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                //write all JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                //use the JSON simple library to parse the string into a json object
                JSONParser parser = new JSONParser();
                JSONObject data_obj = (JSONObject) parser.parse(inline);
                //get required object from the above created object
                System.out.println("Pretty Print of JSON:");
                System.out.println(data_obj.toString()); // pretty print json
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Could not connect to API");
        }
        */
        //BasicNetwork appNetwork = BasicNetwork(HurlStack());
        //DiskBasedCache appCache = DiskBasedCache(cacheDir, 1024*1024);
        //requestQueue = new RequestQueue(appCache, appNetwork);
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