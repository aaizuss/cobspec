package com.aaizuss;

import com.aaizuss.handler.Handler;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

import java.io.IOException;

public class App {
    public static void main( String[] args ) {
        int port = ArgParser.getPort(args, 5000);
        Router router = setupRouter();
        Server server = new Server(router);
        try {
            server.run(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Router setupRouter() {
        Router router = new Router();
        router.addRoute(RequestMethods.GET, "/", new MyHandler());
        return router;
    }

    public static class MyHandler implements Handler {
        public Response execute() {
            return new Response(Status.OK);
        }
    }
}