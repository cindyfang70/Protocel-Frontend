package com.example.protocel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Protocol;

public class ProtocolActivity extends AppCompatActivity {

    Protocols referencingProtocol;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_activity);
        Intent intent = getIntent();

        //setting up bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(ProtocolActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(ProtocolActivity.this, OrganismTypeActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.navigation_favourites:
                        Toast.makeText(ProtocolActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent favouritesIntent = new Intent(ProtocolActivity.this, FavouritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.navigation_recents:
                        Toast.makeText(ProtocolActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent recentsIntent = new Intent(ProtocolActivity.this, RecentsActivity.class);
                        startActivity(recentsIntent);
                        break;
                }
                return true;
            }
        });

        this.referencingProtocol = (Protocols) intent.getSerializableExtra("PROTOCOL");

        this.webView = findViewById(R.id.protocolWebView);

        // Setup the web view and load the data
        this.webView.setWebViewClient(new ProtocolWebViewClient(this));
        Map<String, String> map = new HashMap<>();
        map.put("username", "spongebob");
        map.put("token", "token");
        String protocolURL = "https://www.philippeyu.ca/protocols/README";
        this.webView.loadUrl(protocolURL, map);
    }
}
