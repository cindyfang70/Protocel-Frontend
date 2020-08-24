package com.example.protocel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
