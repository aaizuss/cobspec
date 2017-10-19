package com.aaizuss.routing;

import com.aaizuss.*;
import com.aaizuss.datastore.MockDirectory;
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
import static junit.framework.TestCase.assertFalse;

public class FileSystemRouterTest {
    private static FileSystemRouter router;
    private static MockDirectory mockDirectory;

    @BeforeClass
    public static void onlyOnce() {
        String[] contents = {"journey", "puppies", "text-file.txt"};
        mockDirectory = MockDirectory.withPathStringAndContents("/test-directory/", contents);
    }

    @Before
    public void setUp() {
        router = new FileSystemRouter(mockDirectory);
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
        String[] innerContents = {"broccoli.png"};
        MockDirectory mockInner = MockDirectory.withPathStringAndContents("test-directory/puppies/", innerContents);
        Request puppy = new Request("GET","/broccoli.png");
        Router subject = new FileSystemRouter(mockInner);
        Response response = subject.getResponse(puppy);

        assertEquals(Status.OK, response.getStatus());
    }

    @Test
    public void testResourceRoutes() {
        router.addResourceRoute("/form", new FormHandler(new MemoryResource()));
        Request request = new Request("GET","/form");
        assertFalse(router.getResponse(request).getStatus().equals(Status.NOT_FOUND));
    }
}
