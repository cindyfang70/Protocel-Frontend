package com.example.protocel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrganismTypeActivity extends AppCompatActivity {
    private ArrayList<Category> organismTypes = new ArrayList<>();
    private ConcurrentRadixTree searchTree = new ConcurrentRadixTree(new DefaultCharArrayNodeFactory());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organism_type);
        Intent intent = getIntent();
        ArrayList<String> login_info = intent.getStringArrayListExtra("LOGIN");
        try {
        RetrieveProtocolTask retrieveProtocol = new RetrieveProtocolTask();
        JSONObject allData = retrieveProtocol.execute(login_info).get();

        // Iterate through all the keys for the top level of the data, and add the categories to it
        for (Iterator<String> it = allData.keys(); it.hasNext(); ) {
            // Get the key for this index
            String key = it.next();
            ArrayList<Category> newCats = new ArrayList<>();
            // Add the subcategories
            try {
                // We want to get the individual category/protocol objects,
                // so we have to get down to it
                JSONObject cellType = allData.getJSONObject(key);
                JSONArray categories = cellType.getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject index = categories.getJSONObject(i);
                    newCats.add(new Category(index));
                }
                this.organismTypes.add(new Category(key, newCats));
            }  catch (JSONException e) {
                e.printStackTrace();
            }


            // Add something for this key to organismTypes
            try {
                JSONObject type = allData.getJSONObject(key);
                JSONObject categories = type.getJSONObject(key);
                organismTypes.add(new Category((categories)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        }  catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
        initializeTree();
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.organism_recycler_view);
        OrganismAdapter adapter = new OrganismAdapter(this, organismTypes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeTree() {
        for (Category c:
             this.organismTypes) {
            addCategoryToTree(c);
        }
    }


    private void addCategoryToTree(Category cat) {
        this.searchTree.put(cat.getName(), cat);
        if (cat.isProtocols()) {
            for (Protocols p:
                 cat.getProtocols()) {
                this.searchTree.put(p.getName(), p);
            }
        } else {
            for (Category c:
                 cat.getSubcategories()) {
                this.addCategoryToTree(c);
            }
        }
    }
}
