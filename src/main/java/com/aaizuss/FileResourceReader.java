package com.aaizuss;

import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.ContentRange;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Hashtable;

public class FileResourceReader extends ResourceReader {

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
}
