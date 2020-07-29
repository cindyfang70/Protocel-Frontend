package com.example.protocel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OrganismTypeActivity extends AppCompatActivity {
    private ArrayList<String> organismTypes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organism_type);
        Intent intent = getIntent();
        ArrayList<String> login_info = intent.getStringArrayListExtra("LOGIN");
        try {
        RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
        JSONObject data = retrieveProtocol.execute(login_info).get();

        }  catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.organism_recycler_view);
        organismTypes.add("Prokaryotes");
        organismTypes.add("Eukaryotes");
        ProtocolAdapter adapter = new ProtocolAdapter(this, organismTypes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}