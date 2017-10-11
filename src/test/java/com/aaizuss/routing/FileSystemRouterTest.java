package com.aaizuss.routing;

import com.aaizuss.*;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.datastore.MockRootDirectory;
import com.aaizuss.handler.FileHandler;
import com.aaizuss.handler.FormHandler;
import com.aaizuss.handler.Handler;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FileSystemRouterTest {
    private static FileSystemRouter router;
    private static DataStore directory;

    @BeforeClass
    public static void onlyOnce() {
        directory = new MockRootDirectory();
    }

    @Before
    public void setUp() {
        router = new FileSystemRouter(directory);
    }

    @Test
    public void testReturnsFileHandlerForDirectoryRequest() {
        Request directoryRequest = new Request("GET", "/");
        Handler handler = router.getHandler(directoryRequest);

        assertEquals(FileHandler.class, handler.getClass());
    }

    @Test
    public void testGetResponseForInvalidDirectoryRequest() {
        Request invalidDirectoryRequest = new Request("GET","/foo");
        Response response = router.getResponse(invalidDirectoryRequest);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testGetResponseForValidImageRequest() {
        Request puppy = new Request("GET","/puppies/broccoli.png");
        Response response = router.getResponse(puppy);

        assertEquals(Status.OK, response.getStatus());
    }

//    @Test
//    public void testResourceRoutes() {
//        router.addResourceRoute("/form", new FormHandler(new FormResource()));
//        Request request = new Request("GET","/form");
//        Handler handler = router.getHandler(request);
//
//        assertTrue(handler instanceof FormHandler);
//    }
}
