package com.example.protocel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);

        //setting up bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(RecentsActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(RecentsActivity.this, OrganismTypeActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.navigation_favourites:
                        Toast.makeText(RecentsActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent favouritesIntent = new Intent(RecentsActivity.this, FavouritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.navigation_recents:
                        Toast.makeText(RecentsActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent recentsIntent = new Intent(RecentsActivity.this, RecentsActivity.class);
                        startActivity(recentsIntent);
                        break;
                }
                return true;
            }
        });
    }
}
