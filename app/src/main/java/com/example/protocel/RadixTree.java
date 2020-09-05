package com.example.protocel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class RadixTree {

    ArrayList<String> key = new ArrayList<>();
    ArrayList<Searchable> value = new ArrayList<>();
    ArrayList<RadixTree> subtrees = new ArrayList<>();
    boolean isRoot = true;

    public RadixTree() {

    }

    public RadixTree(ArrayList<String> key , ArrayList<Searchable> value,
                     ArrayList<RadixTree> subtrees) {
        this.key.addAll(key);
        this.value.addAll(value);
        this.subtrees.addAll(subtrees);
        this.isRoot = false;
    }

    public boolean isLeaf() {
        return this.subtrees.size() == 0 && this.key.size() == 0 && this.value.size() != 0;
    }

    public boolean isEmpty() {
        return this.subtrees.size() == 0 && this.value.size() == 0 && this.key.size() == 0;
    }

    public void insert(Searchable value, String key) {
        String lowercase = key.toLowerCase();
        String[] tempKeyArray = lowercase.split("");
        ArrayList<String> keyArray = new ArrayList<>();
        keyArray.addAll(Arrays.asList(tempKeyArray));
        if (this.key.equals(keyArray)) {
            this.value.add(value);
        } else if (this.isEmpty()) {
            this.key = keyArray;
            this.value.add(value);
        } else if (this.isLeaf()) {
            ArrayList<String> similarities = this.keySimilarities(keyArray);
            if (similarities.size() == this.key.size()) {
                ArrayList<Searchable> temp = new ArrayList<>();
                temp.add(value);
                RadixTree subtree = new RadixTree(keyArray, temp, new ArrayList<RadixTree>());
                this.subtrees.add(subtree);
            } else {
                // Change the key to be the similarities, add 2 new subtrees
                ArrayList<String> oldKey = new ArrayList<>();
                oldKey.addAll(this.key);
                this.key = similarities;

                RadixTree newNode = new RadixTree(oldKey, this.value, this.subtrees);

                this.subtrees = new ArrayList<>();
                this.value = new ArrayList<>();
                this.subtrees.add(newNode);

                ArrayList<Searchable> temp = new ArrayList<>();
                temp.add(value);
                RadixTree newValue = new RadixTree(keyArray, temp, new ArrayList<RadixTree>());
                this.subtrees.add(newValue);
            }
        } else {
            ArrayList<String> similarities = this.keySimilarities(keyArray);
            if ((!this.key.isEmpty() && similarities.size() > 0) && !(this.key.equals(similarities))) {
                // We can take the similarities from here
                // We know it isn't a root, so we have to demote the entire node
                ArrayList<String> oldKey = new ArrayList<>();
                oldKey.addAll(this.key);
                this.key = similarities;

                // Demote the current node
                RadixTree demotedNode = new RadixTree(oldKey, this.value, this.subtrees);
                this.value = new ArrayList<>();
                this.subtrees = new ArrayList<>();
                this.subtrees.add(demotedNode);

                if (this.keySimilarities(keyArray).size() == keyArray.size()) {
                    // Same key, so put it here
                    this.value.add(value);
                } else {
                    // Different key, so we add a subtree
                    ArrayList<Searchable> temp = new ArrayList<>();
                    temp.add(value);
                    RadixTree subtree = new RadixTree(keyArray, temp, new ArrayList<RadixTree>());
                    this.subtrees.add(subtree);
                }

            } else {
                Optional<RadixTree> matching = this.findMatchingSubtree(keyArray);
                if (matching.isPresent()) {
                    ArrayList<String> matchingKey = matching.get().keySimilarities(keyArray);
                    if (matchingKey.equals(this.key)) {
                        ArrayList<Searchable> temp = new ArrayList<>();
                        temp.add(value);
                        RadixTree newNode = new RadixTree(keyArray, temp, new ArrayList<RadixTree>());
                        this.subtrees.add(newNode);
                    } else {
                        matching.get().insert(value, key);
                    }
                } else {
                    ArrayList<Searchable> temp = new ArrayList<>();
                    temp.add(value);
                    RadixTree newNode = new RadixTree(keyArray, temp, new ArrayList<RadixTree>());
                    this.subtrees.add(newNode);
                }
            }
        }
    }

    ArrayList<String> keySimilarities(ArrayList<String> key) {
        int maxLength = Math.min(key.size(), this.key.size());
        int index = 0;
        while (index < maxLength) {
            if (key.get(index).equals(this.key.get(index))) {
                break;
            }
            index++;
        }
        ArrayList<String> newArray = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            newArray.add(key.get(i));
        }
        return newArray;
    }

    Optional<RadixTree> findMatchingSubtree(ArrayList<String> key) {
        Optional<RadixTree> mostMatching = Optional.empty();
        int matchingCount = 0;
        for (RadixTree tree:
             this.subtrees ) {
            int count = tree.keySimilarities(key).size();
            if (count > matchingCount) {
                matchingCount = count;
                mostMatching = Optional.of(tree);
            }
        }
        return mostMatching;
    }

    private boolean isCompleteMatch(ArrayList<String> first, ArrayList<String> second) {
        int minimum = Math.min(first.size(), second.size());
        int index = 0;
        while (index < minimum) {
            if (!first.get(index).equals(second.get(index))) {
                return false;
            }
            index++;
        }
        return true;
    }

    public ArrayList<Searchable> iterateThroughSubtrees() {
        if (this.isLeaf()) {
            return this.value;
        } else {
            ArrayList<Searchable> values = new ArrayList<>();
            values.addAll(this.value);
            for (RadixTree subtree:
                 this.subtrees) {
                values.addAll(subtree.iterateThroughSubtrees());
            }
            return values;
        }
    }
}
