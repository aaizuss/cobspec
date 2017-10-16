package com.aaizuss;

import com.aaizuss.datastore.Directory;
import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class FileResourceReaderTest {

    private static Directory directory;
    private static FileResourceReader reader;

    public static File createTempTxtFile(TemporaryFolder tempFolder) throws IOException {
        File createdFile = tempFolder.newFile("text-file.txt");
        FileResourceWriter.updateResource("/text-file.txt", directory, "I am a text file!", false);
        return createdFile;
    }

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        reader = new FileResourceReader();
        directory = new Directory(tempFolder.getRoot().getPath());
        createTempTxtFile(tempFolder);
    }

    @Test
    public void testGetContentFromURI() throws DirectoryNotFoundException {
        String expected = "I am a text file!";
        String uri = "/text-file.txt";
        String content = new String(reader.getContent(uri, directory));
        assertEquals(expected, content);
    }

    @Test
    public void testGetPartialContent() throws DirectoryNotFoundException {
        Hashtable<String,Integer> range = new Hashtable<>();
        range.put("Start", 5);
        range.put("End", 10);
        String uri = "/text-file.txt";
        String content = new String(reader.getPartialContent(uri, directory, range));
        String expected = "a text";

        assertEquals(expected, content);
    }
}
