package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.Header;
import com.aaizuss.ResourceReader;
import com.aaizuss.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class MediaContentHandler implements Handler {
    private Directory directory;

    public MediaContentHandler(Directory directory) {
        this.directory = directory;
    }

    public Response execute(Request request) {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return getResponse(request);
            case RequestMethods.HEAD:
                return headResponse(request);
            case RequestMethods.OPTIONS:
                return optionsResponse(request);
            default:
                return notAllowedResponse();
        }
    }

    private Response notAllowedResponse() {
        Response response = new Response(Status.METHOD_NOT_ALLOWED);
        response.setHeader(Header.ALLOW, "GET,HEAD,OPTIONS");
        return response;
    }


    private Response optionsResponse(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        response.setHeader(Header.ALLOW, "GET,HEAD,OPTIONS");
        return response;
    }

    private Response headResponse(Request request) {
        return statusAndHeaders(request);
    }

    private Response getResponse(Request request) {
        Response response = statusAndHeaders(request);
        response.setBody(ResourceReader.getContent(request.getUri(), directory));
        return response;
    }

    private Response statusAndHeaders(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        return response;
    }
}
