package com.aaizuss;

import com.aaizuss.datastore.DataStore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileResourceWriter {

//    public static void updateResource(String uri, DataStore directory, String content, boolean append) {
//        String path = directory.getPathToResource(uri);
//        File resource = createResource(path);
//        writeToResource(resource, content, append);
//    }
//
//    public static void deleteDataFromResource(String uri, DataStore directory) {
//        File resource = createResource(directory.getPathToResource(uri));
//        writeToResource(resource, "", false);
//    }
//
//    private static File createResource(String filepath) {
//        File resource = new File(filepath);
//        try {
//            resource.createNewFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resource;
//    }
//
//    private static void writeToResource(File resource, String content, boolean append) {
//        try (FileWriter writer = new FileWriter(resource, append)) {
//            writer.write(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
