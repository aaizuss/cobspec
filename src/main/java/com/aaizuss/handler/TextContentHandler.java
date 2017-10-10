package com.aaizuss.handler;

import com.aaizuss.Header;
import com.aaizuss.ResourceReader;
import com.aaizuss.Status;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class TextContentHandler implements Handler {
    private DataStore directory;

    public TextContentHandler(DataStore directory) {
        this.directory = directory;
    }

    public Response execute(Request request) {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return get(request);
            case RequestMethods.HEAD:
                return head(request);
            case RequestMethods.POST:
                return post(request);
            case RequestMethods.PUT:
                return post(request);
            case RequestMethods.OPTIONS:
                return options(request);
            case RequestMethods.PATCH:
                return new PatchHandler(directory).execute(request);
            default: return new Response(Status.METHOD_NOT_ALLOWED);
        }
    }

    private Response get(Request request) {
        if (request.isPartial()) {
            return partialResponse(request);
        } else {
            return fullResponse(request);
        }
    }

    private Response head(Request request) {
        if (request.isPartial()) {
            Response response = partialResponse(request);
            response.setBody("");
            return response;
        } else {
            Response response = fullResponse(request);
            response.setBody("");
            return response;
        }
    }

    private Response post(Request request) {
        if (directory.containsResource(request.getUri())) {
            return new Response(Status.METHOD_NOT_ALLOWED);
        } else {
            return new Response(Status.NOT_IMPLEMENTED);
        }
    }

    private Response options(Request request) {
        String methods = "GET,HEAD,POST,OPTIONS,PUT";
        if (directory.containsResource(request.getUri())) {
            methods = "GET,HEAD,OPTIONS";
        }
        Response response = new Response(Status.OK);
        response.setHeader(Header.ALLOW, methods);
        return response;
    }

    private Response partialResponse (Request request) {
        Response response = new Response(Status.PARTIAL);
        response.setHeader(Header.CONTENT_RANGE, request.getHeader(Header.RANGE));
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        response.setBody(ResourceReader.getPartialContent(request.getUri(), directory, request.getContentRange()));
        return response;
    }

    private Response fullResponse(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        response.setBody(ResourceReader.getContent(request.getUri(), directory));
        return response;
    }
}
