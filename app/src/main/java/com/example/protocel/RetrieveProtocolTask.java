package com.example.protocel;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.example.protocel.Category;
import com.example.protocel.Protocols;

class RetrieveProtocolTask extends AsyncTask<String, JSONObject, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("username", "cindy")
                .header("token", "50000$owvO1h9w$829b9ce880eedae09a2f696f061c6797bff8f61037a91c904f6bc211c41b3f87")
                .url("https://www.philippeyu.ca/get_protocols")
                .post(requestBody)
                .build();
        Response responses = null;
        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Response finalResponses = responses;
        final String[] protocolData = {null};
        try {
            protocolData[0] = finalResponses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject data = new JSONObject(protocolData[0]);
            return data;
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

}
