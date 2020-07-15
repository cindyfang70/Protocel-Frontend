package com.example.protocel;
import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.jacksonandroidnetworking.JacksonParserFactory;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
//import com.example.protocel.ServerInteractions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ArrayList<String> loginInfo = new ArrayList<String>();
                    final TextView emailTextView = findViewById(R.id.email);
                    final TextView passwordTextView = findViewById(R.id.password);
                    String email = emailTextView.getText().toString();
                    String password = passwordTextView.getText().toString();
                    loginInfo.add(email);
                    loginInfo.add(password);
                    Login login = new Login();
                    JSONObject loginResult = login.execute(loginInfo).get();
                    LoginHandler loginHandler = new LoginHandler();
                    boolean loginSuccess = loginHandler.checkResponse(loginResult);
                    if (loginSuccess == false) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                        final EditText et = new EditText(MainActivity.this);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(et);

                        // set dialog message
                        alertDialogBuilder.setMessage("Incorrect username or password").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                    else if (loginSuccess){
                        try {
                            retrieveData(R.layout.activity_organism_type, loginResult.getString("token"));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                } catch (ExecutionException e){
                    e.printStackTrace();
                }
                catch (InterruptedException err){
                    err.printStackTrace();
                }
            }
        });

    }
    public void retrieveData(View view, String token){
        Intent intent = new Intent(this, OrganismTypeActivity.class);
//        try {
//            RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
//            JSONObject data = retrieveProtocol.execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException err) {
//            err.printStackTrace();
//        }
        intent.putExtra("TOKEN", token);
        startActivity(intent);
    }
}
