package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.http.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    @Test
    public void givenFileDoesNotExistItReturnsANotFound()
    {
        MockDirectory directory = MockDirectory.emptyDirectory();

        FileHandler2 subject = new FileHandler2(directory);
        Request request = new Request(RequestMethods.GET, "nonExistentFile");

        Response response = subject.execute(request);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void givenRequestForATextFileInDirectoryItReturnsOk() {
        MockDirectory directory = MockDirectory.withFile("text-file.txt");

        FileHandler2 subject = new FileHandler2(directory);
        Request request = new Request(RequestMethods.GET, "/text-file.txt");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("text/plain", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void givenRequestForAnImageFileInDirectoryItReturnsOk() {
        MockDirectory directory = MockDirectory.withFile("png-file.png");

        FileHandler2 subject = new FileHandler2(directory);
        Request request = new Request(RequestMethods.GET, "/png-file.png");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("image/png", response.getHeader(Header.CONTENT_TYPE));
    }


}
