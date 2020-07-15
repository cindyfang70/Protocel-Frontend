package com.example.protocel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class OrganismTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organism_type);
//        try {
//        RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
//        JSONObject data = retrieveProtocol.execute().get();
//        }  catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException err) {
//            err.printStackTrace();
//        }
    }
}
