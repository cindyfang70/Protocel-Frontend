package com.example.protocel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Intent intent = getIntent();

        //setting up bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(FavouritesActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(FavouritesActivity.this, OrganismTypeActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.navigation_favourites:
                        Toast.makeText(FavouritesActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent favouritesIntent = new Intent(FavouritesActivity.this, FavouritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.navigation_recents:
                        Toast.makeText(FavouritesActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent recentsIntent = new Intent(FavouritesActivity.this, RecentsActivity.class);
                        startActivity(recentsIntent);
                        break;
                }
                return true;
            }
        });



    }
}
