package com.aaizuss;

import com.aaizuss.datastore.*;
import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryHtmlCreatorTest {
    private static DirectoryHtmlCreator htmlCreator;
    private static DataStore rootDirectory;
    private static DataStore puppiesDirectory;

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        rootDirectory = new MockRootDirectory();
        puppiesDirectory = new MockInnerDirectory();
        htmlCreator = new DirectoryHtmlCreator(puppiesDirectory, rootDirectory);
    }

    @Test
    public void testGetLinkStringForInnerDirectory() {
        String expected = "<a href='/'>< Back</a></br>\r\n" +
                "<a href='/puppies/broccoli.png'>broccoli.png</a></br>\r\n" +
                "<a href='/puppies/pup1.jpg'>pup1.jpg</a></br>\r\n";
        assertEquals(expected, htmlCreator.getLinkString());
    }

    @Test
    public void testGetLinkStringForRootDirectory() {
        String expected =
                "<a href='/journey'>journey</a></br>\r\n" +
                        "<a href='/puppies'>puppies</a></br>\r\n" +
                        "<a href='/text-file.txt'>text-file.txt</a></br>\r\n";
        DirectoryHtmlCreator creator = new DirectoryHtmlCreator(rootDirectory, rootDirectory);
        assertEquals(expected, creator.getLinkString());
    }

}
