package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.http.Header;
import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MediaContentHandlerTest {

    private String[] directoryContents = {"broccoli.png", "pup1.jpg"};
    private MockDirectory mockDirectory = MockDirectory.withPathStringAndContents("/test-directory/puppies/", directoryContents);
    private MediaContentHandler handler;
    private Request jpgRequest = new Request("GET", "/pup1.jpg");

    @Test
    public void givenImageRequestResponseIsOKAndContainsContentType() {
        handler = new MediaContentHandler(mockDirectory);

        Response response = handler.execute(jpgRequest);

        assertEquals(Status.OK, response.getStatus());
        assertEquals("image/jpeg", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void testPostRequestReturnsMethodNotAllowed() {
        Request postRequest = new Request("POST", "/pup1.jpg");
        handler = new MediaContentHandler(mockDirectory);

        Response response = handler.execute(postRequest);

        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void testOptionsRequest() {
        Request optionsRequest = new Request("OPTIONS", "/pup1.jpg");
        handler = new MediaContentHandler(mockDirectory);

        Response response = handler.execute(optionsRequest);

        assertEquals(Status.OK, response.getStatus());
        assertEquals("GET,HEAD,OPTIONS", response.getHeader(Header.ALLOW));
    }
}
