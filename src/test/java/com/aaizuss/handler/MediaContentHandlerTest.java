package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MediaContentHandlerTest {
    private Directory directory;
    private MediaContentHandler handler;
    private Request pngRequest = new Request("GET", "/image.png");

    @Before
    public void setUp() throws IOException, DirectoryNotFoundException {
        File mockFolder = new File("mockFolder");
        mockFolder.mkdir();
        File pngFile = File.createTempFile("image", ".png", mockFolder);
        File txtFile = File.createTempFile("text-file", ".txt", mockFolder);
        File htmlFile = File.createTempFile("index", ".html", mockFolder);

        FileWriter writer = new FileWriter(txtFile);
        writer.write("I am a text file.");
        writer.flush();
        writer.close();

        mockFolder.deleteOnExit();
        pngFile.deleteOnExit();
        txtFile.deleteOnExit();
        htmlFile.deleteOnExit();

        directory = new Directory(mockFolder.getAbsolutePath());
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
