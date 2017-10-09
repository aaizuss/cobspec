package com.aaizuss;

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

    public static void populateForTextContentTests(TemporaryFolder folder) {
        try {
            writeTempTextFile(folder);
            folder.newFile("file1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void populateWithEmptyFiles(TemporaryFolder folder, String[] names) {
        for (String name : names) {
            try {
                folder.newFile(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeTempTextFile(TemporaryFolder folder) throws IOException {
        File file = folder.newFile("partial_content.txt");
        try{
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("This is a file that contains text to read part of in order to fulfill a 206.");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
