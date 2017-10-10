package com.aaizuss.handler;

import com.aaizuss.datastore.Directory;
import com.aaizuss.ResourceReader;
import com.aaizuss.Status;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

public class FileHandler implements Handler {
    private DataStore directory;

    public FileHandler(DataStore directory) {
        this.directory = directory;
    }

    public Response execute(Request request) {
        if (canServeDirectory(request)) {
            try {
                return setupDirectoryHandler(request).execute(request);
            } catch (DirectoryNotFoundException e) {
                e.printStackTrace();
                return new Response(Status.NOT_FOUND);
            }
        } else if (directory.containsResource(request.getUri())) {
            return responseForContentType(request);
        } else {
            return new Response(Status.NOT_FOUND);
        }
    }

    private Response responseForContentType(Request request) {
        String uri = request.getUri();
        if (ResourceReader.getContentType(uri).contains("text")) {
            return new TextContentHandler(directory).execute(request);
        } else {
            return new MediaContentHandler(directory).execute(request);
        }
    }

    private DirectoryHandler setupDirectoryHandler(Request request) throws DirectoryNotFoundException {
        String newPath = directory.getPathString() + formatUriForDirectory(request.getUri());
        Directory changedDirectory = new Directory(newPath);
        return new DirectoryHandler(changedDirectory, directory);
    }

    private String formatUriForDirectory(String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        } else {
            return uri;
        }
    }

    private boolean canServeDirectory(Request request) {
        String path = directory.getPathString() + formatUriForDirectory(request.getUri());
        return Directory.isFolder(path) || request.getUri().equals("/");
    }


}
