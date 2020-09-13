package com.example.protocel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FavoritesAsyncTask extends AsyncTask<String, String, ArrayList<Protocols>> {

    private final Context mcontext;

    public FavoritesAsyncTask(final Context context){
        super();
        mcontext = context;
    }

    @Override
    protected ArrayList<Protocols> doInBackground(String... strings) {
        ReadLoginFromFile readLogin = new ReadLoginFromFile();
        String info = readLogin.readFromFile(mcontext);
        String[] split_info = info.split(":");
        String username = split_info[0];
        username = username.replace("\n", "");
        String token = split_info[1];

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("token", token)
                .header("username", username)
                .url("https://www.philippeyu.ca/retrieve_liked_protocols")
                .post(requestBody)
                .build();

        Response responses = null;
        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Response finalResponses = responses;
        String jsonBody = "";

        try {
            jsonBody = finalResponses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray favoritesResponseJSON = new JSONArray(jsonBody);
            ArrayList<Protocols> protocolArray = new ArrayList<>();

            for (int i = 0; i < favoritesResponseJSON.length(); i++) {
                JSONObject protocolData = favoritesResponseJSON.getJSONObject(i);
                Protocols p = new Protocols(protocolData);
                protocolArray.add(p);
            }
            return protocolArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
//                mainHandler.post(new Runnable(){
//                    @Override
//                    public void run() {
//                        // TODO: Setup the table view
//                        String responseBody = "";
//                        try {
//                            responseBody = response.body().string();
//                        } catch (IOException e) {
//                            Log.d("io exception","Error getting the response body string");
//                        }
//
//                        ArrayList<Protocols> protocolArray = new ArrayList<>();
//                        try {
//                            // Need 2 because for some reason, it returns in a nested array
//                            JSONArray data = new JSONArray(responseBody);
//                            JSONArray actualData = data.getJSONArray(0);
//                            for (int i = 0; i < actualData.length(); i++) {
//                                JSONObject protocolData = actualData.getJSONObject(i);
//                                Protocols p = new Protocols(protocolData);
//                                protocolArray.add(p);
//                            }
//                            setupRecyclerView(protocolArray);
//                        }
//                        catch (JSONException e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });

    }
}
