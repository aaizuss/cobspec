package com.aaizuss.handler;

import com.aaizuss.MemoryResource;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicAuthHandlerTest {
    private static BasicAuthHandler handler;
    private Response response;

    @BeforeClass
    public static void setUp() {
        handler = new BasicAuthHandler();
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
    public void givenRequestWithValidCredentialsReturnsShowsSillyCobspecStuffInBody() {
        Request request = new Request("GET", "/logs");
        request.addHeader(Header.AUTHORIZATION, "Basic YWRtaW46aHVudGVyMg==");
        response = handler.execute(request);

        String expectedLog = "GET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1\n";
        assertEquals(expectedLog, new String(response.getBody()));
    }

}
