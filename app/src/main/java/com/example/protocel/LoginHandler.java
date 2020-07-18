package com.example.protocel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginHandler {
    public boolean checkResponse(JSONObject loginData){

        if (loginData.has("error")){

            return false;
        }
        else if (loginData.has("token")){
            try {
                String token = loginData.getString("token");
            } catch (JSONException e){
                e.printStackTrace();
            }
//            try {
//                RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
//                JSONObject data = retrieveProtocol.execute().get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException err) {
//                err.printStackTrace();
//            }
            return true;
        }
        return false;
    }
}
