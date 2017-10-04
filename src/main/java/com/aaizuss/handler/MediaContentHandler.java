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

    public MediaContentHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response execute() {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return getResponse();
            case RequestMethods.HEAD:
                return headResponse();
            case RequestMethods.OPTIONS:
                return optionsResponse();
            default:
                return notAllowedResponse();
        }
    }

    private Response notAllowedResponse() {
        Response response = new Response(Status.METHOD_NOT_ALLOWED);
        response.setHeader(Header.ALLOW, "GET,HEAD,OPTIONS");
        return response;
    }


    private Response optionsResponse() {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        response.setHeader(Header.ALLOW, "GET,HEAD,OPTIONS");
        return response;
    }

    private Response headResponse() {
        return statusAndHeaders();
    }

    private Response getResponse() {
        Response response = statusAndHeaders();
        response.setBody(ResourceReader.getContent(request.getUri(), directory));
        return response;
    }

    private Response statusAndHeaders() {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        return response;
    }
}
