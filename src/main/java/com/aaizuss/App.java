package com.aaizuss;

import com.aaizuss.datastore.Directory;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.handler.*;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.routing.FileSystemRouter;

import java.io.IOException;

public class App {
    public static String DEFAULT_DIRECTORY = System.getProperty("user.dir") + "/public/";

    public static void main( String[] args ) {
        int port = ArgParser.getPort(args, 5000);
        String directory = ArgParser.getDirectory(args, DEFAULT_DIRECTORY);
        Router router = setupRouter(directory);
        Server server = new Server(router);
        ArgParser.printConfig(port, directory);
        try {
            server.run(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Router setupRouter(String directoryPath) {
        try {
            Directory directory = new Directory(directoryPath);
            FileSystemRouter router = new FileSystemRouter(directory);
            addOptionsRoutes(router);
            router.addResourceRoute("/form", new FormHandler(new MemoryResource()));
            router.addRoute(RequestMethods.GET, "/parameters", new ParameterDecode());
            router.addRoute(RequestMethods.GET, "/redirect", new RedirectHandler());
            router.addRoute(RequestMethods.GET, "/coffee", new CoffeeTeaHandler());
            router.addRoute(RequestMethods.GET, "/tea", new CoffeeTeaHandler());
            router.addRoute(RequestMethods.GET, "/cookie", new CookieHandler());
            router.addRoute(RequestMethods.GET, "/eat_cookie", new CookieHandler());
            return router;
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }
        return new Router();
    }

    public static void addOptionsRoutes(Router router) {
        router.addRoute(RequestMethods.GET, "/method_options", new OptionsHandler());
        router.addRoute(RequestMethods.OPTIONS, "/method_options", new OptionsHandler());
        router.addRoute(RequestMethods.POST, "/method_options", new OptionsHandler());
        router.addRoute(RequestMethods.PUT, "/method_options", new OptionsHandler());
        router.addRoute(RequestMethods.HEAD, "/method_options", new OptionsHandler());
        router.addRoute(RequestMethods.GET, "/method_options2", new OptionsHandler());
        router.addRoute(RequestMethods.OPTIONS, "/method_options2", new OptionsHandler());
    }
}