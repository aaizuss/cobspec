package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryTest {
    private Directory directory;

    @Before
    public void setUp() throws DirectoryNotFoundException {
        directory = new Directory(System.getProperty("user.dir") + "/test-directory/");
    }

    @Test
    public void testDirectoryConstructorReturnsPublicDirectory() {
        Directory defaultDirectory = new Directory();
        String expected = System.getProperty("user.dir") + "/public/";
        assertEquals(expected, defaultDirectory.getPathString());
    }

    @Test
    public void testDirectoryConstructorWithArgument() {
        String expected =  System.getProperty("user.dir") + "/test-directory/";
        assertEquals(expected, directory.getPathString());
    }

    @Test
    public void testGetContents() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("empty-folder");
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
        String nestedPath = System.getProperty("user.dir") + "/test-directory/puppies/pup1.jpg";
        String requestPath = "/text-file.txt";
        String path = System.getProperty("user.dir") + "/test-directory/text-file.txt";

        assertEquals(nestedPath, directory.getPathToResource(nestedRequestPath));
        assertEquals(path, directory.getPathToResource(requestPath));
    }


    @Test
    public void testGetParentPathRestrictions() throws DirectoryNotFoundException{
        Directory inner = new Directory(System.getProperty("user.dir") + "/test-directory/puppies/");
        assertEquals("", directory.getParentPathString(directory));
        assertEquals("/", inner.getParentPathString(directory));
    }

    @Test
    public void testGetResourceName() {
        assertEquals("pup1.jpg", directory.getResourceName("/puppies/pup1.jpg"));
    }
}
