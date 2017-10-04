package com.aaizuss.routing;

import com.aaizuss.Directory;
import com.aaizuss.Status;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.handler.DirectoryHandler;
import com.aaizuss.handler.Handler;
import com.aaizuss.handler.MediaContentHandler;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;


public class FileHandler implements Handler {
    private Request request;
    private Directory directory;

    public FileHandler(Request request, Directory directory) {
        this.request = request;
        this.directory = directory;
    }

    public Response execute() {
        if (canServeDirectory()) {
            try {
                return setupDirectoryHandler(request).execute();
            } catch (DirectoryNotFoundException e) {
                e.printStackTrace();
                return new Response(Status.NOT_FOUND);
            }
        } else if (directory.containsResource(request.getUri())) {
            return new MediaContentHandler(request, directory).execute();
        } else {
            return new Response(Status.NOT_FOUND);
        }
    }

    private DirectoryHandler setupDirectoryHandler(Request request) throws DirectoryNotFoundException {
        String newPath = directory.getPathString() + formatUriForDirectory(request.getUri());
        Directory changedDirectory = new Directory(newPath);
        return new DirectoryHandler(request, changedDirectory, directory);
    }

    private String formatUriForDirectory(String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        } else {
            return uri;
        }
    }

    private boolean canServeDirectory() {
        String path = directory.getPathString() + formatUriForDirectory(request.getUri());
        return Directory.isFolder(path) || request.getUri().equals("/");
    }


}