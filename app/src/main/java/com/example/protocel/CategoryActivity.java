package com.example.protocel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Protocols> protocols = new ArrayList<>();
    boolean isProtocols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();

        // Get the category data that was passed through
        Category categoryData = (Category) intent.getSerializableExtra("SUBCATEGORY");
        this.isProtocols = categoryData.isProtocols();
        if (categoryData.isProtocols()) {
            // Deal with protocols
            this.protocols.addAll(categoryData.getProtocols());
        } else {
            // Deal with categories
            this.categories.addAll(categoryData.getSubcategories());
        }

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);

        if (this.isProtocols) {
//            CategoryAdapter adapter = new CategoryAdapter(this, organismTypes);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            CategoryAdapter adapter = new CategoryAdapter(this, this.categories);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
