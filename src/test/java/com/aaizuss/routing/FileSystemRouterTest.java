package com.aaizuss.routing;

import com.aaizuss.*;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.handler.FileHandler;
import com.aaizuss.handler.FormHandler;
import com.aaizuss.handler.Handler;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FileSystemRouterTest {
    private static String ROOT = TestConstants.TEST_DIR;
    private FileSystemRouter router;
    private Request directoryRequest = new Request("GET", "/");
    private Directory directory;

    @Before
    public void setUp() throws DirectoryNotFoundException {
        directory = new Directory(ROOT);
        router = new FileSystemRouter(directory);
    }

    @Test
    public void testGetHandlerForValidDirectoryRequest() {
        Handler handler = router.getHandler(directoryRequest);

        assertTrue(handler instanceof FileHandler);
    }

    @Test
    public void testGetHandlerForInvalidDirectoryRequestReturnsFileHandler() {
        Request invalidDirectoryRequest = new Request("GET","/foo");
        Handler handler = router.getHandler(invalidDirectoryRequest);

        assertTrue(handler instanceof FileHandler);
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
        assertEquals("image/png", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void testResourceRoutes() {
        router.addResourceRoute("/form", new FormHandler(new FormResource()));
        Request request = new Request("GET","/form");
        Handler handler = router.getHandler(request);

        assertTrue(handler instanceof  FormHandler);
    }
}
