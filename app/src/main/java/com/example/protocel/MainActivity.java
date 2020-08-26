package com.example.protocel;
import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jacksonandroidnetworking.JacksonParserFactory;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
//import com.example.protocel.ServerInteractions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
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
                    Login login = new Login(MainActivity.this);
                    JSONObject loginResult = login.execute(loginInfo).get();
                    LoginHandler loginHandler = new LoginHandler();
                    boolean loginSuccess = loginHandler.checkResponse(loginResult);
                    if (!loginSuccess) {
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
                        ArrayList<String> login_info = new ArrayList<String>();
                        login_info.add(email);
                        try {
                            login_info.add(loginResult.getString("token"));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        retrieveData(R.layout.activity_organism_type, login_info);

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
    public void retrieveData(int view, ArrayList<String> login_info){
        Intent intent = new Intent(this, OrganismTypeActivity.class);
        intent.putExtra("LOGIN", login_info);
        startActivity(intent);
    }
}
