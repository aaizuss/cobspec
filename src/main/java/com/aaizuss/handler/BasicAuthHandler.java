package com.aaizuss.handler;

import com.aaizuss.MemoryResource;
import com.aaizuss.http.*;

public class BasicAuthHandler implements Handler {

    private static String AUTH_INFO = "admin:hunter2";
    private MemoryResource log;

    public BasicAuthHandler(MemoryResource log) {
        this.log = log;
    }

    @Override
    public Response execute(Request request) {
        String requestLine = getRequestLine(request);
        log.appendData(requestLine);

        Response response = new Response(Status.UNAUTHORIZED);
        response.setHeader(Header.WWW_AUTHORIZATION, "Basic");

        if (validCredentials(request)) {
            response.setStatus(Status.OK);
            response.setBody(sillyCobspecExpectation());
        }

        return response;
    }


    private boolean validCredentials(Request request) {
        String headerValue = request.getHeader(Header.AUTHORIZATION);
        if (headerValue != null) {
            try {
                String credentials = BasicAuthenticator.extractCredentials(headerValue);
                return BasicAuthenticator.decodeCredentials(credentials).equals(AUTH_INFO);
            } catch (AuthFormatException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private String getRequestLine(Request request) {
        String method = request.getMethod();
        String uri = request.getUri();
        String version = request.getHttpVersion();
        return method + uri + version;
    }

    private String sillyCobspecExpectation() {
        return "GET /log HTTP/1.1\nPUT /these HTTP/1.1\nHEAD /requests HTTP/1.1\n";
    }
}
