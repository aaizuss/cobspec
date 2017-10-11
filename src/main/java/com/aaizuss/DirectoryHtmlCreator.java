package com.aaizuss;

import com.aaizuss.datastore.DataStore;

import java.io.File;

public class DirectoryHtmlCreator {
    private DataStore currentDirectory;
    private DataStore rootDirectory;
    private String parentPath;

    private static String ROOT = "/";
    private static String SEPARATOR = "/";
    private static String NO_PARENT = "";
    private static String BACK = "< Back";
    private static String BACK_ROOT = "< Back to Root";

    public DirectoryHtmlCreator(DataStore rootDirectory) {
        this.currentDirectory = rootDirectory;
        this.rootDirectory = rootDirectory;
        this.parentPath = getParentPathString(rootDirectory, rootDirectory);
    }

    public DirectoryHtmlCreator(DataStore currentDirectory, DataStore rootDirectory) {
        this.currentDirectory = currentDirectory;
        this.rootDirectory = rootDirectory;
        this.parentPath = getParentPathString(currentDirectory, rootDirectory);
    }

    public String getLinkString() {
        String links = getParentLink();
        for (String fileName : currentDirectory.getContents()) {
            String path = linkToResource(currentDirectory, rootDirectory.getPathString(), fileName);
            links += buildHTML(fileName, path);
        }
        return links;
    }

    private String buildHTML(String displayName, String target) {
        String href = hrefFormat(target);
        return "<a href='" + href + "'>" + displayName + "</a></br>\r\n";
    }

    private String hrefFormat(String resourcePath) {
        String target = removeTrailingSlash(resourcePath);
        if (!target.startsWith(SEPARATOR)) {
            return SEPARATOR + target;
        } else {
            return target;
        }
    }

    private String removeTrailingSlash(String path) {
        if (path.endsWith(SEPARATOR)) {
            return path.substring(0, path.length() - 1);
        } else {
            return path;
        }
    }

    private String getParentPathString(DataStore currentDirectory, DataStore rootDirectory) {
        String pathString = currentDirectory.getPathString();
        String rootDirectoryPathString = formatPathString(rootDirectory.getPathString());
        File currentDirectoryFile = new File(pathString);
        String parentPath = currentDirectoryFile.getParent() + "/";

        if (pathString.equals(rootDirectoryPathString)) {
            return "";
        } else if (parentPath.equals(rootDirectoryPathString)) {
            return "/";
        } else {
            String fullParent = formatPathString(pathString) + "..";
            return pathDifference(rootDirectoryPathString, fullParent);
        }
    }

    public String linkToResource(DataStore currentDirectory, String rootDirectory, String uri) {
        String path = currentDirectory.getPathToResource(uri);
        return pathDifference(rootDirectory, path);
    }

    private String getParentLink() {
        if (parentPath.equals(NO_PARENT)) {
            return NO_PARENT;
        } else if (parentPath.equals(ROOT)) {
            String displayText = BACK_ROOT;
            return buildHTML(displayText, parentPath);
        } else {
            String displayText = BACK;
            return buildHTML(displayText, parentPath);
        }
    }

    private static String formatPathString(String pathString) {
        if (pathString.endsWith("/")) {
            return pathString;
        } else {
            return pathString + "/";
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
