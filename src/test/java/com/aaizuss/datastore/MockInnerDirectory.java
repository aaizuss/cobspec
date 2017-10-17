package com.aaizuss.datastore;

import java.util.ArrayList;
import java.util.Hashtable;

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
    public byte[] read(String uri) {
        return new byte[0];
    }

    @Override
    public byte[] partialRead(String uri, Hashtable<String, Integer> range) {
        return new byte[0];
    }

    @Override
    public void writeToResource(String uri, String content, boolean append) {

    }

    @Override
    public void clearDataFromResource(String uri) {

    }

    @Override
    public void delete(String uri) {

    }

}
