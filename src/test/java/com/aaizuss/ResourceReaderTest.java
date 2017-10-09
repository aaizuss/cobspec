package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import static com.aaizuss.ResourceReader.getContent;
import static com.aaizuss.ResourceReader.getPartialContent;
import static com.aaizuss.ResourceReader.getContentType;
import static org.junit.Assert.assertEquals;

public class ResourceReaderTest {

    private static Directory directory;

    public static File createTempTxtFile(TemporaryFolder tempFolder) throws IOException {
        File createdFile = tempFolder.newFile("text-file.txt");
        FileResourceWriter.updateResource("/text-file.txt", directory, "I am a text file!", false);
        return createdFile;
    }

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        directory = new Directory(tempFolder.getRoot().getPath());
        createTempTxtFile(tempFolder);
    }

    @Test
    public void testGetFileType() {
        assertEquals("text/html", getContentType("index.html"));
        assertEquals("text/plain", getContentType("file.txt"));
        assertEquals("application/octet-stream", getContentType("file.mov"));
    }

    @Test
    public void testGetContentFromURI() throws DirectoryNotFoundException {
        String expected = "I am a text file!";
        String uri = "/text-file.txt";
        String content = new String(getContent(uri, directory));
        assertEquals(expected, content);
    }

    @Test
    public void testGetPartialContent() throws DirectoryNotFoundException {
        Hashtable<String,Integer> range = new Hashtable<>();
        range.put("Start", 5);
        range.put("End", 10);
        String uri = "/text-file.txt";
        String content = new String(getPartialContent(uri, directory, range));
        String expected = "a text";

        assertEquals(expected, content);
    }
}
