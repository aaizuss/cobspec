package com.aaizuss.datastore;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TestDirectory {

    public static void populate(TemporaryFolder folder) {
        try {
            folder.newFile("text-file.txt");
            folder.newFolder("journey", "come", "inside", "and-find", "a-surprise", "thanks.txt");
            folder.newFolder("puppies", "pup1.jpg");
            folder.newFolder("puppies", "broccoli.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeContentToTempTextFile(TemporaryFolder folder, String fileName, String content) throws IOException {
        File file = folder.newFile(fileName);
        try{
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
