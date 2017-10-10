package com.aaizuss.datastore;

import com.aaizuss.datastore.DataStore;

import java.util.ArrayList;

public class MockInnerDirectory implements DataStore {

    public boolean containsResource(String identifier) {
        return true;
    }

    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<>();
        contents.add("broccoli.png");
        contents.add("pup1.jpg");
        return contents;
    }

    public String getPathString() {
        return "/test-directory/puppies/";
    }

    public String getPathToResource(String uri) {
        return uri;
    }
}
