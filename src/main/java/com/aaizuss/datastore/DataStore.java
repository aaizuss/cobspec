package com.aaizuss.datastore;

import java.util.ArrayList;

// idea: include ways to read from/write to data in data store
// or isolate that responsibility in a different interface
public interface DataStore {

    boolean containsResource(String identifier);
    ArrayList<String> getContents();
    String getPathString(); // this is very specific to a file system
    String getPathToResource(String uri);
}
