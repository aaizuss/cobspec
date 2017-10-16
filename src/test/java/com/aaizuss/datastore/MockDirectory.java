package com.aaizuss.datastore;

import com.aaizuss.ResourceReader;

import java.util.ArrayList;
import java.util.Hashtable;

public class MockDirectory implements DataStore {

    private String file;
    private ArrayList<String> contents = new ArrayList<>();

    private String folder;
    private ArrayList<String> folderContents = new ArrayList<>();

    public static MockDirectory emptyDirectory() {
        return new MockDirectory();
    }

    public static MockDirectory withFile(String fileName) {
        return new MockDirectory(fileName);
    }

    public static MockDirectory withFolder(String folderName, ArrayList<String> contents) {
        return new MockDirectory(folderName, contents);
    }

    public static MockDirectory withContents(ArrayList<String> contents) {
        return new MockDirectory(contents);
    }

    public MockDirectory() {}

    public MockDirectory(String filename) {
        file = filename;
    }

    public MockDirectory(ArrayList<String> contents) {
        this.contents = contents;
    }

    public MockDirectory(String folderName, ArrayList<String> folderContents) {
        this.folder = folderName;
        this.folderContents = folderContents;
    }

    @Override
    public boolean containsResource(String identifier) {
        for (String item : contents) { //this is dumb but it's a mock
            if (identifier.contains(item)) {
                return true;
            }
        }
        return file != null;
    }

    @Override
    public boolean isFolder(String identifier) {
        return folder != null;
    }

    @Override
    public ArrayList<String> getContents() {
        return contents;
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
                    case "html": return "text/html";
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
