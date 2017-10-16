package com.aaizuss.handler;

import com.aaizuss.ResourceReader;
import com.aaizuss.http.Header;
import com.aaizuss.http.Status;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

public class TextContentHandler extends ContentHandler {
    private DataStore directory;
    private ResourceReader reader;

    public TextContentHandler(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    protected Response get(Request request) {
        if (request.isPartial()) {
            return partialResponse(request);
        } else {
            return fullResponse(request);
        }
    }

    @Override
    protected Response head(Request request) {
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

    @Override
    protected Response post(Request request) {
        if (directory.containsResource(request.getUri())) {
            return new Response(Status.METHOD_NOT_ALLOWED);
        } else {
            return new Response(Status.NOT_IMPLEMENTED);
        }
    }

    @Override
    protected Response patch(Request request) {
        return new PatchHandler(directory).execute(request);
    }

    @Override
    protected String allowedMethods() {
        return "GET,HEAD,POST,OPTIONS,PUT";
    }

    private Response partialResponse (Request request) {
        Response response = new Response(Status.PARTIAL);
        response.setHeader(Header.CONTENT_RANGE, request.getHeader(Header.RANGE));
        response.setHeader(Header.CONTENT_TYPE, reader.getContentType(request.getUri()));
        response.setBody(reader.getPartialContent(request.getUri(), directory, request.getContentRange()));
        return response;
    }

    private Response fullResponse(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, reader.getContentType(request.getUri()));
        response.setBody(reader.getContent(request.getUri(), directory));
        return response;
    }
}
