package com.aaizuss.handler;

import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.*;

public class BasicAuthHandler implements Handler {

    private static String AUTH_INFO = "admin:hunter2";
    private RequestLogger logger;

    public BasicAuthHandler(DataStore directory) {
        this.logger = new RequestLogger(directory);
    }

    @Override
    public Response execute(Request request) {
        logger.logRequest(request);

        Response response = new Response(Status.UNAUTHORIZED);
        response.setHeader(Header.WWW_AUTHORIZATION, "Basic");

        if (validCredentials(request)) {
            response.setStatus(Status.OK);
            response.setBody(logger.getLogs());
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
}
