package com.example.protocel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class OrganismTypeActivity extends AppCompatActivity {
    private ArrayList<Category> organismTypes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organism_type);
        Intent intent = getIntent();
        final ArrayList<String> login_info = intent.getStringArrayListExtra("LOGIN");
        try {
        RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask(OrganismTypeActivity.this);
        JSONObject allData = retrieveProtocol.execute(login_info).get();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(OrganismTypeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(OrganismTypeActivity.this, OrganismTypeActivity.class);
                        homeIntent.putExtra("LOGIN", login_info);
                        startActivity(homeIntent);
                        break;
                    case R.id.navigation_favourites:
                        Toast.makeText(OrganismTypeActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent favouritesIntent = new Intent(OrganismTypeActivity.this, FavouritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.navigation_recents:
                        Toast.makeText(OrganismTypeActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent recentsIntent = new Intent(OrganismTypeActivity.this, RecentsActivity.class);
                        startActivity(recentsIntent);
                        break;
                }
                return true;
            }
        });

            //setting up the upper toolbar
            Toolbar myToolbar = findViewById(R.id.organismtype_toolbar);
            setSupportActionBar(myToolbar);

        // Iterate through all the keys for the top level of the data, and add the categories to it
        for (Iterator<String> it = allData.keys(); it.hasNext(); ) {
            // Get the key for this index
            String key = it.next();
            ArrayList<Category> newCats = new ArrayList<>();
            // Add the subcategories
            try {
                // We want to get the individual category/protocol objects,
                // so we have to get down to it
                JSONObject cellType = allData.getJSONObject(key);
                JSONArray categories = cellType.getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject index = categories.getJSONObject(i);
                    newCats.add(new Category(index));
                }
                this.organismTypes.add(new Category(key, newCats));
            }  catch (JSONException e) {
                e.printStackTrace();
            }


            // Add something for this key to organismTypes
            try {
                JSONObject type = allData.getJSONObject(key);
                JSONObject categories = type.getJSONObject(key);
                organismTypes.add(new Category((categories)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        }  catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.organism_recycler_view);
        OrganismAdapter adapter = new OrganismAdapter(this, organismTypes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.sort_protocols, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ReadLoginFromFile readLogin = new ReadLoginFromFile();
        String info = readLogin.readFromFile(OrganismTypeActivity.this);
        String[] split_info = info.split(":");
        String username = split_info[0];
        username = username.replace("\n", "");
        String token = split_info[1];
        switch (item.getItemId()) {
            case R.id.action_sort:
                OkHttpClient sort_client = new OkHttpClient();
                RequestBody sort_requestBody = new FormBody.Builder()
                        .build();
                Request sort_request = new Request.Builder()
                        .header("token", token)
                        .header("username", username)
                        .url("https://www.philippeyu.ca/like_protocol")
                        .post(sort_requestBody)
                        .build();
                sort_client.newCall(sort_request).enqueue(new Callback() {
                    Handler mainHandler = new Handler(OrganismTypeActivity.this.getMainLooper());
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(OrganismTypeActivity.this, "Sort good", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(OrganismTypeActivity.this, "Sort bad", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return true;


        }
        return true;
    }
}
