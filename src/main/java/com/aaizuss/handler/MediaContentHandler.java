package com.aaizuss.handler;

import com.aaizuss.http.*;
import com.aaizuss.ResourceReader;
import com.aaizuss.datastore.DataStore;

public class MediaContentHandler extends ContentHandler {
    private DataStore directory;

    public MediaContentHandler(DataStore directory) {
        this.directory = directory;
    }

    @Override
    protected String allowedMethods() {
        return "GET,HEAD,OPTIONS";
    }

    @Override
    protected Response head(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        return response;
    }

    @Override
    protected Response get(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, ResourceReader.getContentType(request.getUri()));
        response.setBody(ResourceReader.getContent(request.getUri(), directory));
        return response;
    }
}
