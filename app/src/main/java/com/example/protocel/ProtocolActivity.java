package com.example.protocel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProtocolActivity extends AppCompatActivity {

    Protocols referencingProtocol;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_activity);
        Intent intent = getIntent();

        //setting up bottom navigation bar
//        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        Toast.makeText(ProtocolActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                        Intent homeIntent = new Intent(ProtocolActivity.this, OrganismTypeActivity.class);
//                        startActivity(homeIntent);
//                        break;
//                    case R.id.navigation_favourites:
//                        Toast.makeText(ProtocolActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
//                        Intent favouritesIntent = new Intent(ProtocolActivity.this, FavouritesActivity.class);
//                        startActivity(favouritesIntent);
//                        break;
//                    case R.id.navigation_recents:
//                        Toast.makeText(ProtocolActivity.this, "Recents", Toast.LENGTH_SHORT).show();
//                        Intent recentsIntent = new Intent(ProtocolActivity.this, RecentsActivity.class);
//                        startActivity(recentsIntent);
//                        break;
//                }
//                return true;
//            }
//        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.favourite_protocol, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ReadLoginFromFile readLogin = new ReadLoginFromFile();
        String info = readLogin.readFromFile(ProtocolActivity.this);
        String[] split_info = info.split(":");
        String username = split_info[0];
        username = username.replace("\n", "");
        String token = split_info[1];
        switch (item.getItemId()) {
            case R.id.action_favorite:
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .build();
                Request request = new Request.Builder()
                        .header("token", token)
                        .header("protocol", referencingProtocol.getName())
                        .header("username", username)
                        .url("https://www.philippeyu.ca/like_protocol")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    Handler mainHandler = new Handler(ProtocolActivity.this.getMainLooper());
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(ProtocolActivity.this, "Failed to add to Favourites", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(ProtocolActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



//                    @Override
//                    public void onResponse(final Response response) throws IOException {
//                        mainHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                if (!response.isSuccessful()) {
//                                    return;
//                                }
//                                //cb.onSuccess(response);
//                            }
//                        });
//
//                    }
//                    @Override
//                    public void onFailure(Request request,final Throwable throwable) {
//                        mainHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//
//                    }


//                });

                return true;

//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}
