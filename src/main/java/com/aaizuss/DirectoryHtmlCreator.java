package com.aaizuss;

public class DirectoryHtmlCreator {
    private Directory currentDirectory;
    private Directory rootDirectory;
    private String parentPath;

    private static String ROOT = "/";
    private static String SEPARATOR = "/";
    private static String NO_PARENT = "";
    private static String BACK = "< Back";
    private static String BACK_ROOT = "< Back to Root";

    public DirectoryHtmlCreator(Directory rootDirectory) {
        this.currentDirectory = rootDirectory;
        this.rootDirectory = rootDirectory;
        this.parentPath = rootDirectory.getParentPathString(rootDirectory);
    }

    public DirectoryHtmlCreator(Directory currentDirectory, Directory rootDirectory) {
        this.currentDirectory = currentDirectory;
        this.rootDirectory = rootDirectory;
        this.parentPath = currentDirectory.getParentPathString(rootDirectory);
    }

    public String getLinkString() {
        String links = getParentLink();
        for (String fileName : currentDirectory.getContents()) {
            String path = currentDirectory.linkToResource(rootDirectory.getPathString(), fileName);
            links += buildHTML(fileName, path);
        }
        return links;
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
}
