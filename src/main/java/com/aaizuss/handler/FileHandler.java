package com.aaizuss.handler;

import com.aaizuss.ResourceReader;
import com.aaizuss.datastore.Directory;
import com.aaizuss.FileResourceReader;
import com.aaizuss.http.Status;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

public class FileHandler implements Handler {
    private DataStore directory;
    private ResourceReader reader;

    public FileHandler(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response(Status.NOT_FOUND);

        if (isFolderRequest(request)) {
            return directoryResponse(request);
        } else if (directory.containsResource(request.getUri())) {
            return contentResponse(request);
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
        String newPath = directory.getPathString() + removeLeadingSlash(request.getUri());
        try {
            DataStore changedDirectory = new Directory(newPath);
            return new DirectoryHandler(changedDirectory, directory).execute(request);
        } catch (DirectoryNotFoundException e) {
            return new Response(Status.NOT_FOUND);
        }
    }

    private boolean isFolderRequest(Request request) {
        String uri = directory.getPathString() + removeLeadingSlash(request.getUri());
        return directory.isFolder(uri) || uri.equals("/");
    }


    private String removeLeadingSlash(String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        } else {
            return uri;
        }
    }
}
