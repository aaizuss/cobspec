package com.aaizuss.handler;

import com.aaizuss.FileTypeReader;
import com.aaizuss.ResourceReader;
import com.aaizuss.http.*;
import com.aaizuss.datastore.DataStore;

public class MediaContentHandler extends ContentHandler {
    private DataStore directory;
    private ResourceReader reader;

    public MediaContentHandler(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    protected String allowedMethods() {
        return "GET,HEAD,OPTIONS";
    }

    @Override
    protected Response head(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, FileTypeReader.getType(request.getUri()));
        return response;
    }

    @Override
    protected Response get(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, FileTypeReader.getType(request.getUri()));
        response.setBody(reader.getContent(request.getUri(), directory));
        return response;
    }
}
