package com.aaizuss.datastore;

import com.aaizuss.http.ContentRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.TreeMap;

public class MockDirectory implements DataStore {

    private String pathString;

//    private String file;
//    private String text = "";
//    private ArrayList<String> contents = new ArrayList<>();
    private Hashtable<String,String> filesAndContents = new Hashtable<>();

    private String folder;
    private ArrayList<String> folderContents = new ArrayList<>();

    public static MockDirectory emptyDirectory() {
        return new MockDirectory();
    }

    public static MockDirectory withFile(String fileName) {
        return new MockDirectory(fileName);
    }

    public static MockDirectory withTextFile(String fileName, String text) {
        return new MockDirectory(fileName, text);
    }

    public static MockDirectory withFolder(String folderName, ArrayList<String> contents) {
        return new MockDirectory(folderName, contents);
    }

    public static MockDirectory withContents(ArrayList<String> contents) {
        return new MockDirectory(contents);
    }

    public static MockDirectory withPathStringAndContents(String pathString, String[] contents) {
        return new MockDirectory(pathString, contents);
    }

    public MockDirectory() {}

    public MockDirectory(String filename) {
        filesAndContents.put(filename, "");
    }

    public MockDirectory(ArrayList<String> contents) {
        for (String fileName : contents) {
            filesAndContents.put(fileName, "");
        }
    }

    public MockDirectory(String fileName, String text) {
        filesAndContents.put(fileName, text);
    }

    public MockDirectory(String folderName, ArrayList<String> folderContents) {
        this.folder = folderName;
        this.folderContents = folderContents;
    }

    public MockDirectory(String pathString, String[] contents) {
        this.pathString = pathString;
        for (String fileName : contents) {
            filesAndContents.put(fileName, "");
        }
    }

    public void addFile(String fileName) {
        filesAndContents.put(fileName, "");
    }

    @Override
    public boolean containsResource(String identifier) {
        String resourceName = identifier.substring(1, identifier.length());
        for (String item : filesAndContents.keySet()) { //this is dumb but it's a mock
            if (resourceName.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFolder(String identifier) {
        return folder != null;
    }

    @Override
    public ArrayList<String> getContents() {
        TreeMap<String,String> ordered = new TreeMap<>(filesAndContents);
        ArrayList<String> contents = new ArrayList<>();
        for (String fileName : ordered.keySet()) {
            contents.add(fileName);
        }
        return contents;
    }

    @Override
    public String getPathString() {
        return pathString;
    }

    @Override
    public String getPathToResource(String uri) {
        return pathString + uri;
    }

    @Override
    public byte[] read(String uri) {
        String fileName = uri.substring(1, uri.length());
        return filesAndContents.get(fileName).getBytes();
    }

    @Override
    public byte[] partialRead(String uri, Hashtable<String, Integer> range) {
        byte[] content = read(uri);
        int length = content.length;
        int[] contentRange = ContentRange.getRange(range, length);

        int start = contentRange[0];
        int end = contentRange[1];

        return Arrays.copyOfRange(content, start, end);
    }

    @Override
    public void writeToResource(String uri, String content, boolean append) {
        String fileName = uri.substring(1, uri.length());
        String text = filesAndContents.get(fileName);
        if (append) {
            text += "\n" + content;
            filesAndContents.put(fileName, text);
        } else {
            text = content;
            filesAndContents.put(fileName, text);
        }
    }

    @Override
    public void clearDataFromResource(String uri) {
        String fileName = uri.substring(1, uri.length());
        filesAndContents.put(fileName, "");
    }

    @Override
    public void delete(String uri) {
        String resourceName = uri.substring(0, uri.length());
        filesAndContents.remove(resourceName);
    }
}
