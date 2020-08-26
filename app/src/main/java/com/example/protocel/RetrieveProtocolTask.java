package com.example.protocel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private final Context mcontext;
    public RetrieveProtocolTask(final Context context){
        super();
        mcontext = context;
    }

    @Override
    protected JSONObject doInBackground(ArrayList<String>... login_info) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        String info = readFromFile(mcontext);
        String[] split_info = info.split(":");
        String username = split_info[0];
        username = username.replace("\n", "");
        String token = split_info[1];
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

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("login.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
