package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TextContentHandlerTest {

    private Directory directory;
    private TextContentHandler handler;
    private Request request = new Request(RequestMethods.GET, "/partial_content.txt");
    private Request partialRequest = new Request(RequestMethods.GET, "/partial_content.txt");

    @Before
    public void setUp() throws IOException, DirectoryNotFoundException {
        partialRequest.addHeader(Header.RANGE, "-6");
        directory = new Directory(System.getProperty("user.dir") + "/public/");
        handler = new TextContentHandler(directory);
    }

    @Test
    public void testResponseForRegularRequest() {
        Response response = handler.execute(request);
        String content = "This is a file that contains text to read part of in order to fulfill a 206.\n";
        Assert.assertEquals(Status.OK, response.getStatus());
        assertEquals("text/plain", response.getHeader(Header.CONTENT_TYPE));
        assertEquals(content, new String(response.getBody()));
    }

    @Test
    public void testResponseStatusForPartialRequest() {
        Response response = handler.execute(partialRequest);
        assertEquals(Status.PARTIAL, response.getStatus());
    }

    @Test
    public void testResponseHeadersForPartialRequest() {
        Response response = handler.execute(partialRequest);
        assertEquals("text/plain", response.getHeader(Header.CONTENT_TYPE));
        assertEquals("-6", response.getHeader(Header.CONTENT_RANGE));
    }

    @Test
    public void testResponseBodyForPartialRequestStartAndEnd() {
        partialRequest.addHeader(Header.RANGE, "0-4");
        Response response = handler.execute(partialRequest);
        String expected = "This ";
        assertEquals(expected, new String(response.getBody()));
    }

    @Test
    public void testResponseBodyForPartialRequestOnlyStart() {
        partialRequest.addHeader(Header.RANGE, "4-");
        Response response = handler.execute(partialRequest);
        String expected = " is a file that contains text to read part of in order to fulfill a 206.\n";
        assertEquals(expected, new String(response.getBody()));
    }

    @Test
    public void testResponseBodyForPartialRequestOnlyEnd() {
        Response response = handler.execute(partialRequest);
        String expected = "206.\n";
        assertEquals(expected, new String(response.getBody()));
    }

}
