package com.example.protocel;
import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.jacksonandroidnetworking.JacksonParserFactory;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
    }
}
