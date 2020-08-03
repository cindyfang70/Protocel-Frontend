package com.example.protocel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;


public class Category implements Serializable {

    // Attributes
    private String name; // Required
    private ArrayList<Protocols> protocols; // Optional
    private ArrayList<Category> subcategories; // Optional
    private boolean isProtocols; // Required

    // Initializer
    public Category(JSONObject data) {
        try {
            this.name = data.getString("name");
            if (data.has("categories")) {
                this.subcategories = new ArrayList<Category>();
                JSONArray categories = data.getJSONArray("categories");
                int numCats = categories.length();
                for (int i = 0; i < numCats; i++) {
                    this.subcategories.add(new Category(categories.getJSONObject(i)));
                }
                this.protocols = null;
            } else if (data.has("protocols")) {
                this.protocols = new ArrayList<Protocols>();
                JSONArray protocols = data.getJSONArray("protocols");
                int numProtocols = protocols.length();
                for (int j = 0; j < numProtocols; j++) {
                    this.protocols.add(new Protocols(protocols.getJSONObject(j)));
                }
                this.subcategories = null;
            }
            if (this.subcategories == null) {
                this.isProtocols = true;
            } else {
                this.isProtocols = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Protocols> getProtocols() {
        return protocols;
    }

    public ArrayList<Category> getSubcategories() {
        return subcategories;
    }

    public boolean isProtocols() {
        return isProtocols;
    }
}


