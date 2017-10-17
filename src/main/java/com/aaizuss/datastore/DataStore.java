package com.aaizuss.datastore;

import java.util.ArrayList;
import java.util.Hashtable;

public interface DataStore {

    boolean containsResource(String identifier);
    boolean isFolder(String identifier);
    ArrayList<String> getContents();
    String getPathString();
    String getPathToResource(String uri);

    byte[] read(String uri);
    byte[] partialRead(String uri, Hashtable<String,Integer> range);

    void writeToResource(String uri, String content, boolean append);
    void clearDataFromResource(String uri);
    void delete(String uri);

}
