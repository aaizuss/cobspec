package com.aaizuss.datastore;

import java.util.ArrayList;

public class MockRootDirectory implements DataStore {

    public String getPathString() {
        return "/test-directory/";
    }

    public boolean containsResource(String identifier) {
        ArrayList<String> contents = getContents();
        for (String item : contents) {
            if (identifier.contains(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<>();
        contents.add("journey");
        contents.add("puppies");
        contents.add("text-file.txt");
        return contents;
    }

    public String getPathToResource(String uri) {
        return uri;
    }

}
