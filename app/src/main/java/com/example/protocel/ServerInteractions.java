package com.example.protocel;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ServerInteractions {

    public void getProtocols() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "")
                .url("http://ec2-99-79-171-20.ca-central-1.compute.amazonaws.com/get_protocols")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e){
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
                if (!response.isSuccessful()){
                    throw new IOException("Unexpected Code" + response);
                } else {
                    Log.d("response", "got a response");
                }
            }
        });

    }

}
