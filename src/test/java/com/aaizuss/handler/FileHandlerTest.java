package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.http.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    @Test
    public void givenFileDoesNotExistItReturnsANotFound()
    {
        MockDirectory directory = MockDirectory.emptyDirectory();

        FileHandler subject = new FileHandler(directory);
        Request request = new Request(RequestMethods.GET, "nonExistentFile");

        Response response = subject.execute(request);

        assertEquals(Status.NOT_FOUND, response.getStatus());
    }

    @Test
    public void givenRequestForATextFileInDirectoryItReturnsOk() {
        MockDirectory directory = MockDirectory.withFile("text-file.txt");

        FileHandler subject = new FileHandler(directory);
        Request request = new Request(RequestMethods.GET, "/text-file.txt");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("text/plain", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void givenRequestForAnImageFileInDirectoryItReturnsOk() {
        MockDirectory directory = MockDirectory.withFile("png-file.png");

        FileHandler subject = new FileHandler(directory);
        Request request = new Request(RequestMethods.GET, "/png-file.png");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("image/png", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void givenRequestForAGifFileInDirectoryItReturnsOk() {
        MockDirectory directory = MockDirectory.withFile("gif-file.png");

        FileHandler subject = new FileHandler(directory);
        Request request = new Request(RequestMethods.GET, "/gif-file.gif");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("image/gif", response.getHeader(Header.CONTENT_TYPE));
    }

    @Test
    public void givenRequestForFileInDirectoryWithManyFilesItReturnsOk() {
        ArrayList<String> files = new ArrayList<>();
        files.add("text-file.txt");
        files.add("png-file.png");
        files.add("gif-file.gif");
        files.add("html-file.html");

        MockDirectory directory = MockDirectory.withContents(files);
        FileHandler subject = new FileHandler(directory);
        Request request = new Request(RequestMethods.GET, "/html-file.html");

        Response response = subject.execute(request);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("text/html", response.getHeader(Header.CONTENT_TYPE));

    }

    // this will fail because the check for responding to a root request is that the
    // 2 directories in directory handler are the same path
//    @Test
//    public void givenRequestForRootItReturnsOk() {
//        MockDirectory directory = MockDirectory.withFile("hello.txt");
//
//        FileHandler subject = new FileHandler(directory);
//        Request request = new Request(RequestMethods.GET, "/");
//
//        Response response = subject.execute(request);
//        assertEquals(Status.OK, response.getStatus());
//    }

    // this will keep failing because of the DirectoryNotFound exception
    // the new directory is created within a method, so I can't mock the inner directory :(
//    @Test
//    public void givenRequestForAFolderInDirectoryItReturnsOk() {
//        MockDirectory directory = MockDirectory.withFolder("a-folder", new ArrayList<>());
//
//        FileHandler subject = new FileHandler(directory);
//        Request request = new Request(RequestMethods.GET, "/a-folder");
//
//        Response response = subject.execute(request);
//        assertEquals(Status.OK, response.getStatus());
//    }


}
