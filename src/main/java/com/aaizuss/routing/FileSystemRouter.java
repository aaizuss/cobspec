package com.aaizuss.routing;

import com.aaizuss.Directory;
import com.aaizuss.Router;
import com.aaizuss.Status;
import com.aaizuss.handler.Handler;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

import java.util.Hashtable;

public class FileSystemRouter extends Router {
    private Directory directory;

    public FileSystemRouter(Directory directory) {
        super();
        this.directory = directory;
    }

    public FileSystemRouter(Directory directory, Hashtable<String,Handler> routes) {
        super(routes);
        this.directory = directory;
    }

    @Override
    public Handler getHandler(Request request) {
        System.out.println("routing request: " + request.getUri());
        Handler handler = super.getHandler(request);
        if (handler == null) {
            return new FileHandler(request, directory);
        } else {
            return handler;
        }
    }

    @Override
    public Response getResponse(Request request) {
        Handler handler = getHandler(request);
        if (handler == null) {
            return new Response(Status.NOT_FOUND);
        }
        return handler.execute();
    }
}
