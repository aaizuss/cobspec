package com.aaizuss;

import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.ContentRange;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Hashtable;

public class FileResourceReader extends ResourceReader {

    public String getContentType(String filename) {
        String extension = getExtension(filename);
        return typesMap.getOrDefault(extension, "application/octet-stream");
    }

    public byte[] getContent(String uri, DataStore directory) {
        String filepath = directory.getPathToResource(uri);
        return getContent(filepath);
    }

    public byte[] getPartialContent(String uri, DataStore directory, Hashtable<String,Integer> range) {
        String filepath = directory.getPathToResource(uri);
        int contentLength = getContentLength(filepath);
        int[] contentRange = ContentRange.getRange(range, contentLength);
        byte[] content = getContent(filepath);

        int start = contentRange[0];
        int end = contentRange[1];

        return Arrays.copyOfRange(content, start, end);
    }


    private int getContentLength(String filepath) {
        byte[] content = getContent(filepath);
        return content.length;
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

    private byte[] getContent(String filepath) {
        byte[] content = new byte[0];
        File file = new File(filepath);
        if (file.isFile()) {
            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private String getExtension(String filename) {
        int startIndex = filename.indexOf(".") + 1;
        int endIndex = filename.length();
        return filename.substring(startIndex, endIndex);
    }
}
