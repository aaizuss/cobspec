package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.DirectoryHtmlCreator;
import com.aaizuss.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class DirectoryHandler implements Handler {
    private DirectoryHtmlCreator htmlCreator;
    private Directory directory;
    private Request request;

    public DirectoryHandler(Request request, Directory rootDirectory) {
        this.htmlCreator = new DirectoryHtmlCreator(rootDirectory);
        this.directory = rootDirectory;
        this.request = request;
    }

    public DirectoryHandler(Request request, Directory directory, Directory serverRootDirectory) {
        this.htmlCreator = new DirectoryHtmlCreator(directory, serverRootDirectory);
        this.directory = directory;
        this.request = request;
    }

    public Response execute() {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return get();
            case RequestMethods.HEAD:
                return head();
            default:
                return new Response(Status.METHOD_NOT_ALLOWED);
        }
    }

    private boolean canServeDirectory() {
        String path = directory.getPathString();
        return Directory.isFolder(path) || request.getUri().equals("/");
    }

    private byte[] getLinksAsBytes() {
        return htmlCreator.getLinkString().getBytes();
    }

    private Response get() {
        if (canServeDirectory()) {
            return new Response(Status.OK, getLinksAsBytes());
        } else {
            return new Response(Status.NOT_FOUND);
        }

    }

    private Response head() {
        if (canServeDirectory()) {
            return new Response(Status.OK);
        } else {
            return new Response(Status.NOT_FOUND);
        }
    }
}
