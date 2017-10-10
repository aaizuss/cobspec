package com.aaizuss.datastore;

import java.util.ArrayList;

public class MockInnerDirectory implements DataStore {

    public boolean containsResource(String identifier) {
        ArrayList<String> contents = getContents();
        for (String item : contents) {
            if (identifier.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<>();
        contents.add("broccoli.png");
        contents.add("pup1.jpg");
        return contents;
    }

    public String getPathString() {
        System.out.println("inner path is /test-directory/puppies/");
        return "/test-directory/puppies/";
    }

    public String getPathToResource(String uri) {
        return getPathString() + uri;
    }
}
