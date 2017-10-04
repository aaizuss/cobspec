package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.handler.Handler;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;
import com.aaizuss.routing.FileSystemRouter;

import java.io.IOException;

public class App {
    public static String DEFAULT_DIRECTORY = System.getProperty("user.dir") + "/public/";

    public static void main( String[] args ) {
        int port = ArgParser.getPort(args, 5000);
        String directory = ArgParser.getDirectory(args, DEFAULT_DIRECTORY);
        Router router = setupRouter(directory);
        Server server = new Server(router);
        try {
            server.run(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Router setupRouter(String directoryPath) {
        try {
            Directory directory = new Directory(directoryPath);
            Router router = new FileSystemRouter(directory);
            //router.addRoute(RequestMethods.GET, "/", new MyHandler());
            return router;
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("making standard router");
        return new Router();
    }

//    public static class MyHandler implements Handler {
//        public Response execute() {
//            return new Response(Status.OK);
//        }
//    }
}