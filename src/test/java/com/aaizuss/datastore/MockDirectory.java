package com.aaizuss.datastore;

import com.aaizuss.ResourceReader;

import java.util.ArrayList;
import java.util.Hashtable;

public class MockDirectory implements DataStore {

    private String file;

    public static MockDirectory emptyDirectory() {
        return new MockDirectory();
    }

    public static MockDirectory withFile(String fileName) {
        return new MockDirectory(fileName);
    }

    public MockDirectory() {}

    public MockDirectory(String filename) {
        file = filename;
    }

    @Override
    public boolean containsResource(String identifier) {
        return file != null;
    }

    @Override
    public ArrayList<String> getContents() {
        return null;
    }

    @Override
    public String getPathString() {
        return null;
    }

    @Override
    public String getPathToResource(String uri) {
        return null;
    }

    @Override
    public ResourceReader getResourceReader() {
        return new ResourceReader() {
            @Override
            public String getContentType(String filename) {
                int startIndex = filename.indexOf(".") + 1;
                int endIndex = filename.length();
                String extension = filename.substring(startIndex, endIndex);
                switch (extension) {
                    case "txt": return "text/plain";
                    case "png": return "image/png";
                    case "gif": return "image/gif";
                    default: return "application/octet-stream";
                }
            }

            @Override
            public byte[] getContent(String uri, DataStore directory) {
                return new byte[0];
            }

            @Override
            public byte[] getPartialContent(String uri, DataStore directory, Hashtable<String, Integer> range) {
                return new byte[0];
            }
        };
    }
}
