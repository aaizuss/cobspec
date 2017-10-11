package com.aaizuss.handler;

import com.aaizuss.datastore.*;
import com.aaizuss.http.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {
    private static DataStore directory;
    private static DirectoryHandler handler;
    private Request request = new Request("GET", "/");

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        directory = new MockRootDirectory();
        handler = new DirectoryHandler(directory);
    }

    @Test
    public void testDirectoryResponse() {
        Response response = handler.execute(request);
        String expectedBody = "<a href='/journey'>journey</a></br>\r\n" +
                "<a href='/puppies'>puppies</a></br>\r\n" +
                "<a href='/text-file.txt'>text-file.txt</a></br>\r\n";
        assertEquals(Status.OK, response.getStatus());
        assertEquals(expectedBody, new String(response.getBody()));

    }

    @Test
    public void testPostRequestNotAllowed() {
        Request request = new Request("POST", "/");
        handler = new DirectoryHandler(directory);
        Response response = handler.execute(request);
        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void testInnerDirectory() throws DirectoryNotFoundException {
        DataStore inner = new MockInnerDirectory();
        handler = new DirectoryHandler(inner, directory);
        Response response = handler.execute(request);
        String expectedBody = "<a href='/'>< Back</a></br>\r\n" +
                "<a href='/puppies/broccoli.png'>broccoli.png</a></br>\r\n" +
                "<a href='/puppies/pup1.jpg'>pup1.jpg</a></br>\r\n";
        assertEquals(Status.OK, response.getStatus());
        assertEquals(expectedBody, new String(response.getBody()));

    }
}
