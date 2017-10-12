package com.aaizuss.handler;

import com.aaizuss.http.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CookieHandlerTest {

    private CookieHandler handler = new CookieHandler();
    private Request request;
    private Response response;

    @Test
    public void testSetsCookie() {
        request = new Request(RequestMethods.GET, "/cookie", "type=chocolate", "HTTP/1.1");
        response = handler.execute(request);

        assertEquals(Status.OK, response.getStatus());
        assertEquals("Eat", new String(response.getBody()));
        assertEquals("type=chocolate", response.getHeader(Header.SET_COOKIE));
    }

    @Test
    public void testUsesCookieInResponse() {
        request = new Request(RequestMethods.GET, "/eat_cookie");
        request.addHeader(Header.COOKIE, "type=sugar; other=stuff; perhaps=iguess");
        response = handler.execute(request);

        assertEquals(Status.OK, response.getStatus());
        assertEquals("mmmm sugar", new String(response.getBody()));
    }
}
