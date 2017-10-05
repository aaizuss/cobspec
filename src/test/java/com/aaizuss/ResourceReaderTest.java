package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Test;

import java.util.Hashtable;

import static com.aaizuss.ResourceReader.getContent;
import static com.aaizuss.ResourceReader.getPartialContent;
import static com.aaizuss.ResourceReader.getContentType;
import static org.junit.Assert.assertEquals;

public class ResourceReaderTest {
    @Test
    public void testGetFileType() {
        assertEquals("text/html", getContentType("index.html"));
        assertEquals("text/plain", getContentType("file.txt"));
        assertEquals("application/octet-stream", getContentType("file.mov"));
    }

    @Test
    public void testGetContentFromURI() throws DirectoryNotFoundException {
        String expected = "I am a text file!";
        Directory directory = new Directory(System.getProperty("user.dir") + "/test-directory/");
        String uri = "/text-file.txt";
        String content = new String(getContent(uri, directory));
        assertEquals(expected, content);
    }

    @Test
    public void testGetPartialContent() throws DirectoryNotFoundException {
        Hashtable<String,Integer> range = new Hashtable<>();
        range.put("Start", 5);
        range.put("End", 10);
        Directory directory = new Directory(System.getProperty("user.dir") + "/test-directory/");
        String uri = "/text-file.txt";
        String content = new String(getPartialContent(uri, directory, range));
        String expected = "a text";

        assertEquals(expected, content);
    }
}
