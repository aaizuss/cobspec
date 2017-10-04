package com.aaizuss.handler;

import com.aaizuss.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectHandlerTest {
    private Request request = new Request("GET", "/redirect");
    private Response response = new RedirectHandler().execute(request);

    @Test
    public void testGetStatus() {
        assertEquals("HTTP/1.1 302 Found", response.getStatus());
    }

    @Test
    public void testGetHeadersReturnsContentTypeHeader() {
        assertEquals("/", response.getHeaders().get(Header.LOCATION));
    }

    @Test
    public void testGetBodyReturnsEmptyBody() {
        assertEquals("", new String(response.getBody()));
    }
}
