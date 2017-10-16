package com.aaizuss.datastore;

import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.ContentRange;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

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
    public void testGetResourceName() {
        assertEquals("pup1.jpg", directory.getResourceName("/puppies/pup1.jpg"));
    }

    @Test
    public void testReadReturnsContentOfFile() throws IOException, DirectoryNotFoundException {
        TestDirectory.writeContentToTempTextFile(testDirectory, "hello.txt", "hello there");
        Directory directory = new Directory(testDirectory.getRoot().getPath());

        assertEquals("hello there\n", new String(directory.read("/hello.txt")));
    }

    @Test
    public void testPartialReadReturnsPartialContents() throws IOException, DirectoryNotFoundException {
        String content = "This is a file that contains text to read part of in order to fulfill a 206.\n";
        TestDirectory.writeContentToTempTextFile(testDirectory, "test.txt", content);
        Directory directory = new Directory(testDirectory.getRoot().getPath());

        Hashtable<String,Integer> range = new Hashtable<>();
        range.put(ContentRange.START, 0);
        range.put(ContentRange.END, 4);

        String partial = new String(directory.partialRead("/test.txt", range));
        System.out.println(partial);

        assertEquals("This ", partial);
    }

}
