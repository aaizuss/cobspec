package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.MockDirectory;
import com.aaizuss.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MediaContentHandlerTest {
    private Directory directory;
    private MediaContentHandler handler;
    private Request pngRequest = new Request("GET", "/image.png");

    @Before
    public void setUp() throws IOException, DirectoryNotFoundException {
        directory = MockDirectory.get();
    }

    @Test
    public void testResponseHeaderAndStatus() {
        handler = new MediaContentHandler(directory);
        Response response = handler.execute(pngRequest);
        Assert.assertEquals(Status.OK, response.getStatus());
        assertEquals("image/png", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void testPostRequestReturnsMethodNotAllowed() {
        Request postRequest = new Request("POST", "/image.png");
        handler = new MediaContentHandler(directory);
        Response response = handler.execute(postRequest);
        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void testOptionsRequest() {
        Request optionsRequest = new Request("OPTIONS", "/image.png");
        handler = new MediaContentHandler(directory);
        Response response = handler.execute(optionsRequest);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("GET,HEAD,OPTIONS", response.getHeader(Header.ALLOW));
    }
}
