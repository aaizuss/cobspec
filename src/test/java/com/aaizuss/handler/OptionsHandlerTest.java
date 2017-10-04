package com.aaizuss.handler;

import com.aaizuss.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {
    private OptionsHandler handler = new OptionsHandler();
    private static String ALL = "GET,HEAD,POST,OPTIONS,PUT";
    private static String SOME = "GET,OPTIONS";

    @Test
    public void testReturnsAllMethodsForOptionsRequest() {
        Request optionsRequest = new Request(RequestMethods.OPTIONS, "/method_options");
        Response response = handler.execute(optionsRequest);
        assertEquals(ALL, response.getHeader(Header.ALLOW));
    }

    @Test
    public void testReturnsSomeMethodsForGetRequest() {
        Request request = new Request(RequestMethods.GET, "/method_options");
        Response response = handler.execute(request);
        assertEquals(SOME, response.getHeader(Header.ALLOW));
    }

    @Test
    public void testReturnsSomeMethodsForPostRequest() {
        Request request = new Request(RequestMethods.POST, "/method_options");
        Response response = handler.execute(request);
        assertEquals(SOME, response.getHeader(Header.ALLOW));
    }

    @Test
    public void testReturnsSomeMethodsForOptions2() {
        Request request = new Request(RequestMethods.OPTIONS, "/method_options2");
        Response response = handler.execute(request);
        assertEquals(SOME, response.getHeader(Header.ALLOW));
    }

}
