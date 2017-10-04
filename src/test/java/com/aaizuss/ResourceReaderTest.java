package com.aaizuss;

import org.junit.Test;

import static com.aaizuss.ResourceReader.getContent;
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
    public void testGetContentFromURI() throws Exception {
        String expected = "I am a text file!";
        Directory directory = new Directory(System.getProperty("user.dir") + "/test-directory/");
        String uri = "/text-file.txt";
        String content = new String(getContent(uri, directory));
        assertEquals(expected, content);
    }
}
