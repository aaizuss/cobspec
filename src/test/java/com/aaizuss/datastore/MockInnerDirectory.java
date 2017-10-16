package com.aaizuss.datastore;

import com.aaizuss.FileResourceReader;
import com.aaizuss.ResourceReader;

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

    @Override
    public boolean isFolder(String identifier) {
        return false;
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
        return getPathString() + uri;
    }

    @Override
    public ResourceReader getResourceReader() {
        return new FileResourceReader();
    }
}
