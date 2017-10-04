package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {
    private static String ROOT = System.getProperty("user.dir") + "/test-directory/";
    private Directory directory;
    private DirectoryHandler handler;
    private Request request = new Request("GET", "/");

    @Before
    public void setUp() throws DirectoryNotFoundException, IOException {
        directory = new Directory(ROOT);
        handler = new DirectoryHandler(request, directory);
    }

    @Test
    public void testDirectoryResponse() {
        Response response = handler.execute();
        String expectedBody = "<a href='/empty-folder'>empty-folder</a></br>\r\n" +
                "<a href='/puppies'>puppies</a></br>\r\n" +
                "<a href='/text-file.txt'>text-file.txt</a></br>\r\n";
        assertEquals(Status.OK, response.getStatus());
        assertEquals(expectedBody, new String(response.getBody()));

    }

    @Test
    public void testPostRequestNotAllowed() {
        Request request = new Request("POST", "/");
        handler = new DirectoryHandler(request, directory);
        Response response = handler.execute();
        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void testInnerDirectory() throws DirectoryNotFoundException {
        Directory inner = new Directory(ROOT + "puppies");
        handler = new DirectoryHandler(request, inner, directory);
        Response response = handler.execute();
        String expectedBody = "<a href='/'>< Back to Root</a></br>\r\n" +
                "<a href='/puppies/broccoli.png'>broccoli.png</a></br>\r\n" +
                "<a href='/puppies/pup1.jpg'>pup1.jpg</a></br>\r\n";
        assertEquals(Status.OK, response.getStatus());
        assertEquals(expectedBody, new String(response.getBody()));

    }
}
