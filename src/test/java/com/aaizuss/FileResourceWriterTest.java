package com.aaizuss;

import com.aaizuss.datastore.Directory;
import com.aaizuss.datastore.TestDirectory;
import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileResourceWriterTest {
    private static Directory directory;

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException {
        TestDirectory.populate(tempFolder);
        directory = new Directory(tempFolder.getRoot().getPath());
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
