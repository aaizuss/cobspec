package com.aaizuss.datastore;

import com.aaizuss.FileResourceReader;
import com.aaizuss.ResourceReader;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.ContentRange;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class Directory implements DataStore {
    private String pathString;
    private ArrayList<String> contents = new ArrayList<>();

    public Directory() {
        this.pathString = System.getProperty("user.dir") + "/public/";
        this.contents = initContents();
    }

    public Directory(String directoryPath) throws DirectoryNotFoundException {
        checkDirectory(directoryPath);
        this.pathString = formatPathString(directoryPath);
        this.contents = initContents();
    }

    public ResourceReader getResourceReader() {
        return new FileResourceReader();
    }

    public String getPathString() {
        return formatPathString(pathString);
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public boolean containsResource(String uri) {
        String targetPath = getPathToResource(uri);
        File file = new File(targetPath);
        return file.exists();
    }

    public byte[] read(String uri) {
        String filepath = getPathToResource(uri);
        return readFromPath(filepath);
    }

    public byte[] partialRead(String uri, Hashtable<String,Integer> range) {
        String filepath = getPathToResource(uri);
        byte[] content = readFromPath(filepath);
        int contentLength = content.length;
        int[] contentRange = ContentRange.getRange(range, contentLength);


        int start = contentRange[0];
        int end = contentRange[1];

        return Arrays.copyOfRange(content, start, end);
    }

    private byte[] readFromPath(String filepath) {
        File file = new File(filepath);

        byte[] content = new byte[0];
        if (file.isFile()) {
            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }


    public boolean isFolder(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    // this is used by FileResourceWriter to figure out where to write
    // thinking about changing the interface to require
    // a method that returns a way to write to a resource more broadly
    // and connects to other classes
    public String getPathToResource(String uri) {
        File file = convertRequestPathToFile(uri);
        return file.getPath();
    }

    public String getResourceName(String uri) {
        File file = convertRequestPathToFile(uri);
        return file.getName();
    }

    private File convertRequestPathToFile(String uri) {
        String target = uri;
        if (uri.startsWith("/")) {
            target = uri.substring(1, uri.length());
        }
        return new File(formatPathString(pathString) + target);
    }

    private ArrayList<String> initContents() {
        File file = new File(pathString);
        String[] files = file.list();
        ArrayList<String> fileList = new ArrayList<>();
        for (String entry : files) {
            if (notHiddenFile(entry)) {
                fileList.add(entry);
            }
        }
        return fileList;
    }

    private boolean notHiddenFile(String name) {
        return !name.startsWith(".");
    }

    private static String formatPathString(String pathString) {
        if (pathString.endsWith("/")) {
            return pathString;
        } else {
            return pathString + "/";
        }
    }

    private void checkDirectory(String directoryPath) throws DirectoryNotFoundException {
        File file = new File(directoryPath);
        if (!file.exists()) {
            throw new DirectoryNotFoundException(directoryPath);
        }
    }
}
