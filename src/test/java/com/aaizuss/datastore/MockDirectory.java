package com.aaizuss.datastore;

import com.aaizuss.http.ContentRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class MockDirectory implements DataStore {

    private String pathString;

    private String file;
    private String text = "";
    private ArrayList<String> contents = new ArrayList<>();

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
        file = filename;
    }

    public MockDirectory(ArrayList<String> contents) {
        this.contents = contents;
    }

    public MockDirectory(String fileName, String text) {
        this.file = fileName;
        this.text = text;
    }

    public MockDirectory(String folderName, ArrayList<String> folderContents) {
        this.folder = folderName;
        this.folderContents = folderContents;
    }

    public MockDirectory(String pathString, String[] contents) {
        this.pathString = pathString;
        this.contents = new ArrayList<>(Arrays.asList(contents));
    }

    public void addFile(String fileName) {
        this.contents.add(fileName);
        for (String item : contents) {
            System.out.println(item);
        }
    }

    @Override
    public boolean containsResource(String identifier) {
        String resourceName = identifier.substring(1, identifier.length());

        for (String item : contents) { //this is dumb but it's a mock
            if (resourceName.equals(item)) {
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
        System.out.println(pathString);
        return pathString;
    }

    @Override
    public String getPathToResource(String uri) {
        return pathString + uri;
    }

    @Override
    public byte[] read(String uri) {
        return text.getBytes();
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
        file = uri.substring(0, uri.length());
        if (append) {
            text += content;
        } else {
            text = content;
        }
    }

    @Override
    public void clearDataFromResource(String uri) {
        file = uri.substring(0, uri.length());
        text = "";
    }

    @Override
    public void delete(String uri) {
        String resourceName = uri.substring(0, uri.length());
        if (file.equals(resourceName)) {
            file = null;
        }
    }
}
