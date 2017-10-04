package com.aaizuss.handler;

import com.aaizuss.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterDecodeTest {
    private Response response;
    private Request request;

    @Before
    public void setUp() {
        String params = "variable_1 = Operators <, >";
        request = new Request(RequestMethods.GET, "/parameters", params,"HTTP/1.1");
        response = new ParameterDecode().execute(request);
    }

    @Test
    public void testGetStatus() {
        assertEquals("HTTP/1.1 200 OK", response.getStatus());
    }

    @Test
    public void testGetBodyReturnsParams() {
        String params = "variable_1 = Operators <, >";
        assertEquals(params, new String(response.getBody()));
    }

    @Test
    public void testHeaderHasContentType() {
        assertEquals("text/plain", response.getHeader(Header.CONTENT_TYPE));
    }

}
