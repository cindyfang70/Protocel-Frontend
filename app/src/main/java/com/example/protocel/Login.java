package com.example.protocel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AsyncTask<ArrayList<String>, Integer, JSONObject> {
    private final Context mcontext;
    public Login(final Context context){
        super();
        mcontext = context;
    }
    @Override
    protected JSONObject doInBackground(ArrayList<String> ...login_info){
        String username = login_info[0].get(0);
        String password = login_info[0].get(1);
        Base64.Encoder encoder = Base64.getEncoder();
        String originalString = username + ":" + password;
        String encodedString = encoder.encodeToString(originalString.getBytes());

        String LOGIN_FILE = "login.txt";


        String authToken = "Basic " + encodedString;
        writeToFile(username, encodedString, LOGIN_FILE, mcontext);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("Authorization", authToken)
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
    private void writeToFile(String username, String authToken, String FILE_NAME, Context context) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            fileOutputStream.write(username.getBytes());
            fileOutputStream.write(":".getBytes());
            fileOutputStream.write(authToken.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
