package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BasicAuthHandlerTest {
    private static BasicAuthHandler handler;
    private Response response;

    @BeforeClass
    public static void setUp() {
        handler = new BasicAuthHandler(MockDirectory.emptyDirectory());
    }

    @Test
    public void givenRequestWithNoCredentialsReturns401() {
        Request request = new Request("GET", "/logs");
        response = handler.execute(request);

        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }

    @Test
    public void givenRequestWithNoCredentialsReturnsHeaderWithBasicAuth() {
        Request request = new Request("GET", "/logs");
        response = handler.execute(request);

        assertEquals("Basic", response.getHeader(Header.WWW_AUTHORIZATION));
    }

    @Test
    public void givenRequestWithInvalidCredentialsReturns401() {
        Request request = new Request("GET", "/logs");
        request.addHeader(Header.AUTHORIZATION, "Basic YWRtaW55aHVudGVyMg==");
        response = handler.execute(request);

        assertEquals(Status.UNAUTHORIZED, response.getStatus());
    }

    @Test
    public void givenRequestWithValidCredentialsReturns200() {
        Request request = new Request("GET", "/logs");
        request.addHeader(Header.AUTHORIZATION, "Basic YWRtaW46aHVudGVyMg==");
        response = handler.execute(request);

        assertEquals(Status.OK, response.getStatus());
        assertEquals("Basic", response.getHeader(Header.WWW_AUTHORIZATION));
    }

    @Test
    public void givenRequestWithValidCredentialsShowsLogsInBody() {
        Request request = new Request("GET", "/logs");
        request.addHeader(Header.AUTHORIZATION, "Basic YWRtaW46aHVudGVyMg==");
        response = handler.execute(request);

        String expectedLog = "GET /logs HTTP/1.1";
        assertTrue(new String(response.getBody()).contains(expectedLog));
    }

}
