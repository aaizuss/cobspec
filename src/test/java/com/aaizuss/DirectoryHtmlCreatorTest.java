package com.aaizuss;

import com.aaizuss.datastore.*;
import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryHtmlCreatorTest {
    private static DirectoryHtmlCreator htmlCreator;
//    private static DataStore rootDirectory;
//    private static DataStore puppiesDirectory;

    private static MockDirectory mockRoot;
    private static MockDirectory mockInner;

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
//        rootDirectory = new MockRootDirectory();
//        puppiesDirectory = new MockInnerDirectory();
//        htmlCreator = new DirectoryHtmlCreator(puppiesDirectory, rootDirectory);
        String[] rootContents = {"journey","puppies", "text-file.txt"};
        String[] innerContents = {"broccoli.png", "pup1.jpg"};
        mockRoot = MockDirectory.withPathStringAndContents("/test-directory/", rootContents);
        mockInner = MockDirectory.withPathStringAndContents("/test-directory/puppies/", innerContents);
        htmlCreator = new DirectoryHtmlCreator(mockInner, mockRoot);
    }

    @Test
    public void testGetLinkStringForInnerDirectory() {
        String expected = "<a href='/puppies/..'>< Back</a></br>\r\n" +
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
        //DirectoryHtmlCreator creator = new DirectoryHtmlCreator(rootDirectory, rootDirectory);
        DirectoryHtmlCreator creator = new DirectoryHtmlCreator(mockRoot, mockRoot);
        assertEquals(expected, creator.getLinkString());
    }

}
