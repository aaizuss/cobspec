package com.aaizuss.datastore;

import com.aaizuss.http.ContentRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class MockDirectory implements DataStore {

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

    public void addFile(String fileName) {
        this.contents.add(fileName);
        System.out.println("added " + fileName + " to contents");
        System.out.println("contents now has: ");
        for (String item : contents) {
            System.out.println(item);
        }
    }

    @Override
    public boolean containsResource(String identifier) {
        System.out.println("in mock contains resource");
        String resourceName = identifier.substring(1, identifier.length());
        System.out.println("name  " + resourceName);
        for (String item : contents) { //this is dumb but it's a mock
            System.out.println("item: " + item + " comparing to " + identifier);
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
