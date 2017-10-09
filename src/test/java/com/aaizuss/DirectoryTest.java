package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryTest {
    private static Directory directory;

    @ClassRule
    public static TemporaryFolder testDirectory = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        TestDirectory.populate(testDirectory);
        directory = new Directory(testDirectory.getRoot().getPath());
    }

    @Test
    public void testDirectoryConstructorReturnsPublicDirectory() {
        Directory defaultDirectory = new Directory();
        String expected = System.getProperty("user.dir") + "/public/";
        assertEquals(expected, defaultDirectory.getPathString());
    }

    @Test
    public void testDirectoryConstructorWithArgument() throws DirectoryNotFoundException {
        String expected = testDirectory.getRoot().getPath() + "/";
        assertEquals(expected, directory.getPathString());
    }

    @Test
    public void testGetContents() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("journey");
        expected.add("puppies");
        expected.add("text-file.txt");
        assertEquals(expected, directory.getContents());
    }

    @Test
    public void testHasResource() {
        assertTrue(directory.containsResource("/puppies/broccoli.png"));
        assertTrue(directory.containsResource("text-file.txt"));
        assertFalse(directory.containsResource("foo"));
    }

    @Test
    public void testPathToResource() {
        String nestedRequestPath = "/puppies/pup1.jpg";
        String nestedPath = testDirectory.getRoot().getPath() + nestedRequestPath;
        String requestPath = "/text-file.txt";
        String path = testDirectory.getRoot().getPath() + requestPath;

        assertEquals(nestedPath, directory.getPathToResource(nestedRequestPath));
        assertEquals(path, directory.getPathToResource(requestPath));
    }


    @Test
    public void testGetParentPathRestrictions() throws DirectoryNotFoundException{
        Directory inner = new Directory(testDirectory.getRoot().getPath() + "/puppies/");

        assertEquals("", directory.getParentPathString(directory));
        assertEquals("/", inner.getParentPathString(directory));
    }

    @Test
    public void testGetResourceName() {
        assertEquals("pup1.jpg", directory.getResourceName("/puppies/pup1.jpg"));
    }
}
