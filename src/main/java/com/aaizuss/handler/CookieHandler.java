package com.aaizuss.handler;

import com.aaizuss.CookieParser;
import com.aaizuss.InvalidCookieException;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;

public class CookieHandler implements Handler{

    public Response execute(Request request) {
        String uri = request.getUri();
        if (uri.equals("/cookie")) {
            return setCookieResponse(request);
        } else {
            return useCookieResponse(request);
        }
    }

    private Response setCookieResponse(Request request) {
        String rawCookie = request.getParams();
        Response response = new Response(Status.OK, "Eat".getBytes());
        response.setHeader(Header.SET_COOKIE, rawCookie);
        return response;
    }

    private Response useCookieResponse(Request request) {
        String rawCookies = request.getHeader(Header.COOKIE);
        String cookie = new String();

        try {
            cookie = CookieParser.getCookie(rawCookies, "type");
        } catch (InvalidCookieException e) {
            e.printStackTrace();
        }

        Response response = new Response(Status.OK);
        response.setBody("mmmm " + cookie);
        return response;
    }
}
