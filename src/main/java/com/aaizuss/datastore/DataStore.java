package com.aaizuss.datastore;

import com.aaizuss.ResourceReader;

import java.util.ArrayList;

// idea: include ways to read from/write to data in data store
// or isolate that responsibility in a different interface
public interface DataStore {

    boolean containsResource(String identifier);
    boolean isFolder(String identifier);
    ArrayList<String> getContents();
    String getPathString();
    String getPathToResource(String uri);
    ResourceReader getResourceReader();
}
