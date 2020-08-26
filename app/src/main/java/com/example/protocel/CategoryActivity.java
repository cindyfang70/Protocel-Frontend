package com.example.protocel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Protocol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
}
