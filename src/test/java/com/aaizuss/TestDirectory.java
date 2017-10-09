package com.aaizuss;

import org.junit.rules.TemporaryFolder;

import java.io.IOException;

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

    public static void populateFunStuff(TemporaryFolder folder) {
        try {
            folder.newFile("text-file.txt");
            folder.newFolder("empty-folder");
            folder.newFolder("puppies", "pup1.jpg");
            folder.newFolder("puppies", "broccoli.png");
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
}
