package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PatchHandlerTest {

    private Request request;
    private PatchHandler handler;
    private MockDirectory mockDirectory;
    private String etag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    private String uri = "/temp_file.txt";
    private Response response;

    @Before
    public void setUp() throws DirectoryNotFoundException, IOException {
        mockDirectory = MockDirectory.withTextFile("temp_file.txt", "default content");
        request = new Request("PATCH", uri);
        handler = new PatchHandler(mockDirectory);
    }

    @Test
    public void testResponseStatusForSuccessfulPatch() {
        request.addHeader(Header.IF_MATCH, etag);
        request.setBody("different content");
        response = handler.execute(request);

        assertEquals(Status.NO_CONTENT, response.getStatus());
    }

    @Test
    public void testChangesContentWhenSuccessfulPatch() {
        request.addHeader(Header.IF_MATCH, etag);
        request.setBody("different content");
        response = handler.execute(request);

        String updatedContent = new String(mockDirectory.read(uri));
        assertEquals("different content", updatedContent);
    }

    @Test
    public void testResponseStatusWhenNoMatch() {
        request.addHeader(Header.IF_MATCH, "nah");
        request.setBody("different content");
        response = handler.execute(request);

        assertEquals(Status.PRECONDITION_FAILED, response.getStatus());
    }

    @Test
    public void testDoesNotChangeContentWhenNoMatch() {
        request.addHeader(Header.IF_MATCH, "nah");
        request.setBody("different content");
        response = handler.execute(request);

        String updatedContent = new String(mockDirectory.read(uri));
        assertEquals("default content", updatedContent);
    }
}
