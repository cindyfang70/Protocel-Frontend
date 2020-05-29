package com.example.protocel;
import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.jacksonandroidnetworking.JacksonParserFactory;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import com.example.protocel.ServerInteractions;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("main activity", "aaaa");
        super.onCreate(savedInstanceState);
       ServerInteractions serverInteractions = new ServerInteractions();
       serverInteractions.getProtocols();
    }
}
