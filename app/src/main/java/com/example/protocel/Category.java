package com.example.protocel;

import android.util.Log;

import java.util.ArrayList;


public class Category {

    // Attributes
    private final String name; // Required
    private final ArrayList<Protocols> protocols; // Optional
    private final ArrayList<Category> subcategories; // Optional
    private final boolean isProtocols; // Required

    // Initializer
    private Category(CategoryBuilder builder) {
        this.name = builder.name;
        this.protocols = builder.protocols;
        this.subcategories = builder.subcategories;
        this.isProtocols = builder.isProtocols;
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


    // Builder
    public static class CategoryBuilder {
        private final String name;
        private ArrayList<Protocols> protocols;
        private ArrayList<Category> subcategories;
        private boolean isProtocols;

        public CategoryBuilder(String name) {
            this.name = name;
        }

        public CategoryBuilder addProtocols(ArrayList<Protocols> protocols) {
            this.protocols = new ArrayList<Protocols>();
            this.isProtocols = true;
            this.protocols.addAll(protocols);
            return this;
        }

        public CategoryBuilder addCategories(ArrayList<Category> categories) {
            this.subcategories = new ArrayList<Category>();
            this.isProtocols = false;
            this.subcategories.addAll(categories);
            return this;
        }

        public Category build() {
            Category cat = new Category(this);
            validateCategoryObject(cat);
            return cat;
        }

        private void validateCategoryObject(Category category) {
            if (category.isProtocols) {
                Log.e("Category Builder Error",
                        "Error building category. Subcategories not null");
                assert category.subcategories == null;
            } else {
                Log.e("Category Builder Error",
                        "Error building category. Protocols not null");
                assert category.protocols == null;
            }
        }
    }

}
