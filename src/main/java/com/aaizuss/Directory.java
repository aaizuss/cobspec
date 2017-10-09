package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;

import java.io.File;
import java.util.ArrayList;

public class Directory {
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

    public String getPathString() {
        return formatPathString(pathString);
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public static boolean isFolder(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    public boolean containsResource(String uri) {
        String targetPath = getPathToResource(uri);
        System.out.println("looking for " + targetPath);
        File file = new File(targetPath);
        return file.exists();
    }

    public String getPathToResource(String uri) {
        File file = convertRequestPathToFile(uri);
        return file.getPath();
    }

    public String linkToResource(String rootDirectory, String uri) {
        String path = getPathToResource(uri);
        return pathDifference(rootDirectory, path);
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

    public String getParentPathString(Directory rootDirectory) {
        String rootDirectoryPathString = formatPathString(rootDirectory.getPathString());
        File currentDirectory = new File(pathString);
        String parentPath = currentDirectory.getParent() + "/";


        if (pathString.equals(rootDirectoryPathString)) {
            return "";
        } else if (parentPath.equals(rootDirectoryPathString)) {
            return "/";
        } else {
            String fullParent = formatPathString(pathString) + "..";
            return pathDifference(rootDirectoryPathString, fullParent);
        }
    }

    private static String pathDifference(String rootDirectory, String path) {
        if (rootDirectory == null) {
            return path;
        }
        if (path == null) {
            return rootDirectory;
        }
        int at = indexOfDifference(rootDirectory, path);
        if (at == -1) {
            return "";
        }
        return path.substring(at);
    }

    private static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return -1;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return -1;
    }
}
