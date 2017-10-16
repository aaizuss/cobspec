package com.aaizuss.handler;

import com.aaizuss.FileTypeReader;
import com.aaizuss.http.*;
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
        response.setHeader(Header.CONTENT_TYPE, FileTypeReader.getContentType(request.getUri()));
        return response;
    }

    @Override
    protected Response get(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, FileTypeReader.getContentType(request.getUri()));
        response.setBody(directory.read(request.getUri()));
        return response;
    }
}
