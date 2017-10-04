package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.ResourceReader;
import com.aaizuss.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class MediaContentHandler implements Handler {
    private Request request;
    private Directory directory;

    // not sure if this class should be responsible for making sure the content exists...
    // that might be the role of the application??
    // if i include a check that the directory contains a resource, i can't test with a mock directory :(

    public MediaContentHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response execute() {
        if (supportsRequest()) {
            Response response = new Response(Status.OK);
            response.setBody(ResourceReader.getContent(request.getUri(), directory));
            response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
            return response;
        } else {
            return notAllowedResponse();
        }
    }

    private Response notAllowedResponse() {
        return new Response(Status.METHOD_NOT_ALLOWED);
    }

    private boolean supportsRequest() {
        return request.getMethod().equals(RequestMethods.GET);
    }
}
