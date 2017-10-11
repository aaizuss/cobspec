package com.aaizuss.handler;

import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class CoffeeTeaHandler implements Handler {
    private static final String TEAPOT_MESSAGE = "I'm a teapot";
    private static final String COFFEE_PATH = "/coffee";
    private static final String TEA_PATH = "/tea";

    public Response execute(Request request) {
        String uri = request.getUri();
        String method = request.getMethod();
        Response response = new Response();

        if (method.equals(RequestMethods.GET) && uri.equals(COFFEE_PATH)) {
            response.setStatus(Status.COFFEE_POT);
            response.setBody(TEAPOT_MESSAGE);
        } else if (method.equals(RequestMethods.GET) && uri.equals(TEA_PATH)) {
            response.setStatus(Status.OK);
        }

        return response;
    }
}
