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

class RetrieveProtocolTask extends AsyncTask<ArrayList<String>, JSONObject, JSONObject> {

    @Override
    protected JSONObject doInBackground(ArrayList<String>... login_info) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        String username = login_info[0].get(0);
        String token = login_info[0].get(1);
        Request request = new Request.Builder()
                .header("username", username)
                .header("token", token)
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
