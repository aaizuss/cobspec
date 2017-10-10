package com.aaizuss.datastore;

import com.aaizuss.datastore.DataStore;
import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MockRootDirectory implements DataStore {

    public String getPathString() {
        return "/test-directory/";
    }

    public boolean containsResource(String identifier) {
        return true;
    }

    @Override
    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<>();
        contents.add("text-file.txt");
        contents.add("journey");
        contents.add("puppies");
        return contents;
    }

    public String getPathToResource(String uri) {
        return uri;
    }

//    public static Directory get() throws IOException, DirectoryNotFoundException {
//        File mockFolder = new File("mockFolder");
//        mockFolder.mkdir();
//        File pngFile = File.createTempFile("image", ".png", mockFolder);
//        File txtFile = File.createTempFile("text-file", ".txt", mockFolder);
//        File htmlFile = File.createTempFile("index", ".html", mockFolder);
//
//        FileWriter writer = new FileWriter(txtFile);
//        writer.write("I am a text file.");
//        writer.flush();
//        writer.close();
//
//        mockFolder.deleteOnExit();
//        pngFile.deleteOnExit();
//        txtFile.deleteOnExit();
//        htmlFile.deleteOnExit();
//
//        return new Directory(mockFolder.getAbsolutePath());
//    }
}
