package com.aaizuss;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileTypeReaderTest {

    @Test
    public void testGetTypeRecognizesHtml() {
        assertEquals("text/html", FileTypeReader.getType("index.html"));
    }

    @Test
    public void testGetTypeRecognizesTxt() {
        assertEquals("text/plain", FileTypeReader.getType("file.txt"));
    }

    @Test
    public void testGetTypeRecognizesPng() {
        assertEquals("image/png", FileTypeReader.getType("image.png"));
    }

    @Test
    public void testGetTypeReturnsOctetStreamWhenNoExtension() {
        assertEquals("application/octet-stream", FileTypeReader.getType("file.mov"));
    }
}
