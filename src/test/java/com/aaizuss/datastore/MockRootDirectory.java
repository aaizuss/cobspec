package com.aaizuss.datastore;

import java.util.ArrayList;
import java.util.Hashtable;

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
    public boolean isFolder(String identifier) {
        switch (identifier) {
            case "/journey": return true;
            case "/puppies": return true;
            case "/text-directory": return true;
            default: return false;
        }
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

    @Override
    public byte[] read(String uri) {
        switch (uri) {
            case "/text-file.txt": return "I am a text file!".getBytes();
            default: return new byte[0];
        }
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
