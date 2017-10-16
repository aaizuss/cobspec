package com.aaizuss.datastore;

import com.aaizuss.ResourceReader;

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
    public ResourceReader getResourceReader() {
        return new ResourceReader() {
            @Override
            public String getContentType(String filename) {
                if (filename.contains(".txt")) {
                    return "text/plain";
                } else {
                    return "";
                }
            }

            @Override
            public byte[] getContent(String uri, DataStore directory) {
                switch(uri) {
                    case "text-file.txt": return "I am a text file!".getBytes();
                    default: return new byte[0];
                }
            }

            @Override
            public byte[] getPartialContent(String uri, DataStore directory, Hashtable<String, Integer> range) {
                return new byte[0];
            }
        };
    }

}
