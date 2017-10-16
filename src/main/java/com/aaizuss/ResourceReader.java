package com.aaizuss;

import com.aaizuss.datastore.DataStore;

import java.util.Hashtable;

public abstract class ResourceReader {
    public abstract byte[] getContent(String uri, DataStore directory);
    public abstract byte[] getPartialContent(String uri, DataStore directory, Hashtable<String,Integer> range);
}
