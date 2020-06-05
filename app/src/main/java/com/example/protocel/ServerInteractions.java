package com.example.protocel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerInteractions {

    public void getProtocols() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "")
                .url("http://ec2-99-79-171-20.ca-central-1.compute.amazonaws.com/get_protocols")
                .post(requestBody)
                .build();
        Response responses = null;
        try {
            responses = client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

        Executor executor = new RetrieveProtocolTask();
        final Response finalResponses = responses;
        String protocolData = null;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    protocolData = finalResponses.body().string();
                } catch (IOException e){
                    e.printStackTrace();
                }

                JSONObject protocolJSON;
                try{
                    protocolJSON = new JSONObject(protocolData);
                    JSONArray protocolArray = protocolJSON.getJSONArray("Prokaryotes");
                    String category;
                    int limit = protocolArray.length();
                    String dataStore[] = new String[limit];
                    for (int i = 0; i < limit; i++) {
                        JSONObject categoryObject = protocolArray.getJSONObject(i);
                        category = categoryObject.getString("name");
                        Log.d("name", category);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });


//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e){
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
//                if (!response.isSuccessful()){
//                    throw new IOException("Unexpected Code" + response);
//                } else {
//                    Log.d("response", "got a response");
//
//                }
//            }
//
//
//        });

    }

}
