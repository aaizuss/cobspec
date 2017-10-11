package com.aaizuss.handler;

import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoffeeTeaHandlerTest {
    private Request coffeeRequest = new Request("GET", "/coffee", "HTTP/1.1");
    private Request teaRequest = new Request("GET", "/tea", "HTTP/1.1");
    private Response response;

    @Test
    public void testCoffeeResponse() {
        response = new CoffeeTeaHandler().execute(coffeeRequest);
        assertEquals(Status.COFFEE_POT, response.getStatus());
        assertEquals("I'm a teapot", new String(response.getBody()));
    }

    @Test
    public void testTeaResponse() {
        response = new CoffeeTeaHandler().execute(teaRequest);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("", new String(response.getBody()));
    }
}
