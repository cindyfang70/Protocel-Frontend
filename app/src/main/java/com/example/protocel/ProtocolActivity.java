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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import androidx.core.content.ContextCompat;
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
    private Menu menu;
    Handler uiHandler;
    boolean isLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_activity);
        Intent intent = getIntent();

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
        this.uiHandler = new Handler();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.favourite_protocol, menu);
        this.menu = menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ReadLoginFromFile readLogin = new ReadLoginFromFile();
        final String info = readLogin.readFromFile(ProtocolActivity.this);
        final String[] split_info = info.split(":");
        final String username = split_info[0].replace("\n", "");
        final String token = split_info[1];
        switch (item.getItemId()) {
            case R.id.action_favorite:

                new Thread(new Runnable() {
                    @Override
                    public void run () {
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
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ImageButton favorite_button = findViewById(R.id.action_favorite);
                                        favorite_button.setImageResource(R.drawable.filled_heart_image);
                                    }
                                });
                            }
                        });

                    }
                }).start();


                break;
            case R.id.action_sort:
                OkHttpClient sort_client = new OkHttpClient();
                RequestBody sort_requestBody = new FormBody.Builder()
                        .build();
                Request sort_request = new Request.Builder()
                        .header("token", token)
                        .header("protocol", referencingProtocol.getName())
                        .header("username", username)
                        .url("https://www.philippeyu.ca/like_protocol")
                        .post(sort_requestBody)
                        .build();
                sort_client.newCall(sort_request).enqueue(new Callback() {
                    Handler mainHandler = new Handler(ProtocolActivity.this.getMainLooper());
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(ProtocolActivity.this, "Sort good", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        mainHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(ProtocolActivity.this, "Sort bad", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                return true;


        }
        return true;
    }
}
