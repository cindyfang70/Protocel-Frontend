package com.example.protocel;

import java.util.ArrayList;
import java.util.Optional;

import okhttp3.Protocol;

public class Category {

    public final String name; // Required
    public final ArrayList<Protocols> protocols; // Optional
    public final ArrayList<Category> subcategories; // Optional
    public final boolean isProtocols; // Required

    private Category(CategoryBuilder builder) {
        this.name = builder.name;
        this.protocols = builder.protocols;
        this.subcategories = builder.subcategories;
        this.isProtocols = builder.isProtocols;
    }


    // Builder
    public static class CategoryBuilder {
        private final String name;
        private ArrayList<Protocols> protocols;
        private ArrayList<Category> subcategories;
        private boolean isProtocols = false;

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
                assert category.subcategories == null;
            } else {
                assert category.protocols == null;
            }
        }
    }

}
