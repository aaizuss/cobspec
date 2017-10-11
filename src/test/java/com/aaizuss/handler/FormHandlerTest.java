package com.aaizuss.handler;

import com.aaizuss.FormResource;
import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormHandlerTest {
    private static FormResource resource;
    private static FormHandler handler;
    private Response response;

    @BeforeClass
    public static void setUp() {
        resource = new FormResource();
        handler = new FormHandler(resource);
    }

    @Test
    public void testBogusMethod() {
        Request request = new Request("FANCY", "/form");
        response = handler.execute(request);
        assertEquals(Status.METHOD_NOT_ALLOWED, response.getStatus());
    }

    @Test
    public void testPostUpdatesBody() {
        Request request = new Request("POST", "/form");
        request.setBody("hello!");
        response = handler.execute(request);
        assertEquals("hello!", new String(response.getBody()));
    }

    @Test
    public void testDataIsPersistent() {
        Request request = new Request("GET", "/form");
        response = handler.execute(request);
        assertEquals("hello!", new String(response.getBody()));
    }
}
