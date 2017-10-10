package com.aaizuss.routing;

import com.aaizuss.Router;
import com.aaizuss.Status;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.handler.FileHandler;
import com.aaizuss.handler.Handler;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;

import java.util.Hashtable;

public class FileSystemRouter extends Router {
    private DataStore directory;
    private Hashtable<String, Handler> resourceRoutes;

    public FileSystemRouter(DataStore directory) {
        super();
        this.directory = directory;
        this.resourceRoutes = new Hashtable<>();
    }

    public FileSystemRouter(DataStore directory, Hashtable<String,Handler> routes) {
        super(routes);
        this.directory = directory;
    }

    @Override
    public Handler getHandler(Request request) {
        System.out.println("routing request: " + request.getUri());
        Handler handler = super.getHandler(request);
        if (handler == null && resourceRoutes.containsKey(request.getUri())) {
            return getResourceHandler(request);
        } else if (handler == null) {
            return new FileHandler(directory);
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
        return handler.execute(request);
    }

    private Handler getResourceHandler(Request request) {
        return resourceRoutes.get(request.getUri());
    }

    public void addResourceRoute(String uri, Handler handler) {
        resourceRoutes.put(uri, handler);
    }
}
