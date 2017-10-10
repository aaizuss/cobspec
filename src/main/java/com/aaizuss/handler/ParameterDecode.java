package com.aaizuss.handler;

import com.aaizuss.http.Header;
import com.aaizuss.http.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

public class ParameterDecode implements Handler {
    public Response execute(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, "text/plain");
        response.setBody(request.getParams());
        return response;
    }
}
