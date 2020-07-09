package com.example.protocel;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AsyncTask<ArrayList<String>, Integer, JSONObject> {
    @Override
    protected JSONObject doInBackground(ArrayList<String> ...login_info){
        String username = login_info[0].get(0);
        String password = login_info[0].get(1);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("username", username)
                .header("token", password)
                .url("https://www.philippeyu.ca/login")
                .post(requestBody)
                .build();
        Response responses = null;
        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Response finalResponses = responses;
        final String[] loginResponse = {null};
        try {
            loginResponse[0] = finalResponses.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject loginResponseJSON = new JSONObject(loginResponse[0]);
            return loginResponseJSON;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
