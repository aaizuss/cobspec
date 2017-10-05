package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileResourceWriterTest {
    private static Directory directory;

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException {
        directory = new Directory(TestConstants.TEST_DIR);
    }

    @Test
    public void testUpdateResourceChangesFileContent() {
        String uri = "/stuff";
        String content = "here is my data";
        FileResourceWriter.updateResource(uri, directory, content, false);

        File resource = new File(directory.getPathToResource(uri));
        content = new String(ResourceReader.getContent(uri, directory));
        assertTrue(content.equals("here is my data"));
        assertTrue(resource.exists());

        resource.delete();
        assertFalse(resource.exists());
    }

    @Test
    public void testDeleteDataFromResource() {
        String uri = "/testing_delete.txt";
        FileResourceWriter.updateResource(uri, directory, "blah blah", false);

        String content = new String(ResourceReader.getContent(uri, directory));
        assertTrue(content.equals("blah blah"));

        FileResourceWriter.deleteDataFromResource(uri, directory);
        content = new String(ResourceReader.getContent(uri, directory));
        assertTrue(content.equals(""));

        File resource = new File(directory.getPathToResource(uri));
        resource.delete();
        assertFalse(resource.exists());
    }

}
