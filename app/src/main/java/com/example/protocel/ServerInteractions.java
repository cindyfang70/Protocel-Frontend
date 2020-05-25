package com.example.protocel;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;


public class ServerInteractions {

    public void get_headings(){
        AndroidNetworking.get("https://10.0.2.2:8080/get_protocol")
                .addHeaders("")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("waluigi", response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("waluigi", anError.toString());

                    }
                });
    }
}
