package com.aaizuss.handler;

import com.aaizuss.datastore.Directory;
import com.aaizuss.datastore.TestDirectory;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHandlerTest {

    private static Directory directory;
    private static FileHandler handler;

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException {
        TestDirectory.populate(tempFolder);
        directory = new Directory(tempFolder.getRoot().getPath());
        handler = new FileHandler(directory);
    }

    @Test
    public void testUsesDirectoryHandlerForDirectoryRequest() {
        Request request = new Request("GET", "/");
        Response response = handler.execute(request);
        String content = new String(response.getBody());
        // this is a dumb test but i don't have a way to test the kind of handler
        // because of the way the function is written
        assertTrue(content.contains("text-file.txt"));
    }

}
