package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHandlerTest {

    private static Directory directory;
    private static FileHandler handler;

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException {
        directory = new Directory(System.getProperty("user.dir") + "/public/");
        handler = new FileHandler(directory);
    }

    @Test
    public void testUsesDirectoryHandlerForDirectoryRequest() {
        Request request = new Request("GET", "/");
        Response response = handler.execute(request);
        String content = new String(response.getBody());
        // this is a dumb test but i don't have a way to test the kind of handler
        // because of the way the function is written
        assertTrue(content.contains("file1"));
    }

    @Test
    public void testUsesMediaHandlerForImageRequest() {
        Request request = new Request("GET", "/image.gif");
        Response response = handler.execute(request);
        String contentType = response.getHeader(Header.CONTENT_TYPE);
        // this is a dumb test but i don't have a way to test the kind of handler
        // because of the way the function is written
        assertEquals("image/gif", contentType);
    }


}
