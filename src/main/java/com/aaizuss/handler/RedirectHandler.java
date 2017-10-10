package com.aaizuss.handler;

import com.aaizuss.http.Header;
import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

public class RedirectHandler implements Handler {

    public Response execute(Request request) {
        Response response = new Response();
        response.setStatus(Status.FOUND);
        response.setHeader(Header.LOCATION, "/");
        return response;
    }
}
