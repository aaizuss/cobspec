package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MockDirectory {
    public static Directory get() throws IOException, DirectoryNotFoundException {
        File mockFolder = new File("mockFolder");
        mockFolder.mkdir();
        File pngFile = File.createTempFile("image", ".png", mockFolder);
        File txtFile = File.createTempFile("text-file", ".txt", mockFolder);
        File htmlFile = File.createTempFile("index", ".html", mockFolder);

        FileWriter writer = new FileWriter(txtFile);
        writer.write("I am a text file.");
        writer.flush();
        writer.close();

        mockFolder.deleteOnExit();
        pngFile.deleteOnExit();
        txtFile.deleteOnExit();
        htmlFile.deleteOnExit();

        return new Directory(mockFolder.getAbsolutePath());
    }

}
