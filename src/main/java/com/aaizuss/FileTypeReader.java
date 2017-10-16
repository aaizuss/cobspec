package com.aaizuss;

import java.util.Hashtable;

public class FileTypeReader {

    public enum FileType {
        IMAGE, TEXT, UNSUPPORTED
    }

    public static FileType fileType(String filename) {
        String type = getContentType(filename);
        if (type.contains("text")) {
            return FileType.TEXT;
        } else if (type.contains("image")) {
            return FileType.IMAGE;
        } else {
            return FileType.UNSUPPORTED;
        }
    }

    public static String getContentType(String filename) {
        String extension = getExtension(filename);
        return typesMap.getOrDefault(extension, "application/octet-stream");
    }

    private static final Hashtable<String,String> typesMap = createTypesMap();
    private static Hashtable<String,String> createTypesMap() {

        Hashtable<String,String> typesMap = new Hashtable<>();

        typesMap.put("txt", "text/plain");
        typesMap.put("html", "text/html");
        typesMap.put("png", "image/png");
        typesMap.put("jpeg", "image/jpeg");
        typesMap.put("jpg", "image/jpeg");
        typesMap.put("gif", "image/gif");
        typesMap.put("md", "text/markdown");
        return typesMap;
    }

    private static String getExtension(String filename) {
        int startIndex = filename.indexOf(".") + 1;
        int endIndex = filename.length();
        return filename.substring(startIndex, endIndex);
    }
}
