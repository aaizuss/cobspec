package com.aaizuss.handler;

import com.aaizuss.ResourceReader;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.datastore.Directory;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;

public class FileHandler2 implements Handler {

    private DataStore directory;
    private ResourceReader reader;

    public FileHandler2(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response(Status.NOT_FOUND);

        if (isFolderRequest(request)) {
            response = directoryResponse(request);
        } else if (directory.containsResource(request.getUri())) {
            response = contentResponse(request);
        }
        return response;
    }

    private Response contentResponse(Request request) {
        if (reader.getContentType(request.getUri()).contains("text")) {
            return new TextContentHandler(directory).execute(request);
        } else {
            return new MediaContentHandler(directory).execute(request);
        }
    }

    private Response directoryResponse(Request request) {
        String newPath = directory.getPathString() + removeTrailingSlash(request.getUri());
        try {
            DataStore changedDirectory = new Directory(newPath);
            return new DirectoryHandler(changedDirectory, directory).execute(request);
        } catch (DirectoryNotFoundException e) {
            return new Response(Status.NOT_FOUND);
        }
    }

    private boolean isFolderRequest(Request request) {
        String uri = request.getUri();
        return directory.isFolder(uri) || uri.equals("/");
    }

    private String removeTrailingSlash(String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        } else {
            return uri;
        }
    }
}
