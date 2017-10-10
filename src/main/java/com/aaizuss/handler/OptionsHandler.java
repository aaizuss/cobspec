package com.aaizuss.handler;

import com.aaizuss.http.Header;
import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class OptionsHandler implements Handler {

    private static String ALL = "GET,HEAD,POST,OPTIONS,PUT";
    private static String SOME = "GET,OPTIONS";

    public Response execute(Request request) {
        if (request.getMethod().equals(RequestMethods.OPTIONS) && request.getUri().equals("/method_options")) {
            return optionsResponse();
        } else {
            return limitedOptionsResponse();
        }
    }

    public Response optionsResponse() {
        Response response = new Response(Status.OK);
        response.setHeader(Header.ALLOW, ALL);
        return response;
    }

    public Response limitedOptionsResponse() {
        Response response = new Response(Status.OK);
        response.setHeader(Header.ALLOW, SOME);
        return response;
    }

}
