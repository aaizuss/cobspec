package com.aaizuss.handler;

import com.aaizuss.Directory;
import com.aaizuss.DirectoryHtmlCreator;
import com.aaizuss.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

public class DirectoryHandler implements Handler {
    private DirectoryHtmlCreator htmlCreator;

    public DirectoryHandler(Directory rootDirectory) {
        this.htmlCreator = new DirectoryHtmlCreator(rootDirectory);
    }

    public DirectoryHandler(Directory directory, Directory serverRootDirectory) {
        this.htmlCreator = new DirectoryHtmlCreator(directory, serverRootDirectory);
    }

    public Response execute(Request request) {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return new Response(Status.OK, getLinksAsBytes());
            case RequestMethods.HEAD:
                return new Response(Status.OK);
            default:
                return new Response(Status.METHOD_NOT_ALLOWED);
        }
    }

    private byte[] getLinksAsBytes() {
        return htmlCreator.getLinkString().getBytes();
    }
}
