package com.aaizuss.datastore;

import java.util.ArrayList;

public class MockDirectory implements DataStore {

    private String file;

    public static MockDirectory emptyDirectory() {
        return new MockDirectory();
    }

    public static MockDirectory withFile(String fileName)
    {
        return new MockDirectory(fileName);
    }

    public MockDirectory()
    {
    }

    public MockDirectory(String filename)
    {
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
}
