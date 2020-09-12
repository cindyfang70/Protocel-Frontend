package com.example.protocel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Protocols> protocols = new ArrayList<>();
    boolean isProtocols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();

        //setting up the upper toolbar
        Toolbar myToolbar = findViewById(R.id.category_toolbar);
        setSupportActionBar(myToolbar);

        //setting up the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(CategoryActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(CategoryActivity.this, OrganismTypeActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.navigation_favourites:
                        Toast.makeText(CategoryActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent favouritesIntent = new Intent(CategoryActivity.this, FavouritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.navigation_recents:
                        Toast.makeText(CategoryActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent recentsIntent = new Intent(CategoryActivity.this, RecentsActivity.class);
                        startActivity(recentsIntent);
                        break;
                }
                return true;
            }
        });

        // Get the category data that was passed through
        Category categoryData = (Category) intent.getSerializableExtra("SUBCATEGORY");
        this.isProtocols = categoryData.isProtocols();
        if (categoryData.isProtocols()) {
            // Deal with protocols
            this.protocols.addAll(categoryData.getProtocols());
        } else {
            // Deal with categories
            this.categories.addAll(categoryData.getSubcategories());
        }

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);

        if (this.isProtocols) {
            ProtocolAdapter adapter = new ProtocolAdapter(this, protocols);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            CategoryAdapter adapter = new CategoryAdapter(this, this.categories);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.sort_protocols, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ReadLoginFromFile readLogin = new ReadLoginFromFile();
        String info = readLogin.readFromFile(CategoryActivity.this);
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
                    Handler mainHandler = new Handler(CategoryActivity.this.getMainLooper());
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(CategoryActivity.this, "Sort good", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(CategoryActivity.this, "Sort bad", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return true;


        }
        return true;
    }
}
