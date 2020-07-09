package com.example.protocel;
import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.jacksonandroidnetworking.JacksonParserFactory;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
//import com.example.protocel.ServerInteractions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
        try {
            JSONObject data = retrieveProtocol.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }

        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ArrayList<String> loginInfo = new ArrayList<String>();
                    final TextView emailTextView = findViewById(R.id.email);
                    final TextView passwordTextView = findViewById(R.id.password);
                    String email = (String)emailTextView.getText();
                    String password = (String)passwordTextView.getText();
                    loginInfo.add(email);
                    loginInfo.add(password);
                    Login login = new Login();
                    JSONObject loginResult = login.execute(loginInfo).get();
                    try {
                        Log.d("WAAHAHHAHAH", loginResult.getString("error"));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } catch (ExecutionException e){
                    e.printStackTrace();
                }
                catch (InterruptedException err){
                    err.printStackTrace();
                }
            }
        });

    }
}
