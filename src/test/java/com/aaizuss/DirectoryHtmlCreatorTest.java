package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryHtmlCreatorTest {
    private static DirectoryHtmlCreator htmlCreator;
    private static Directory rootDirectory;
    private static Directory puppiesDirectory;
    private static Directory journeyDirectory;

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUp() throws DirectoryNotFoundException, IOException {
        TestDirectory.populate(tempFolder);
        rootDirectory = new Directory(tempFolder.getRoot().getPath());
        puppiesDirectory = new Directory(tempFolder.getRoot().getPath() + "/puppies/");
        journeyDirectory = new Directory(tempFolder.getRoot().getPath() + "/journey/");
        htmlCreator = new DirectoryHtmlCreator(puppiesDirectory, rootDirectory);
    }

    @Test
    public void testGetLinkStringForInnerDirectory() {
        String expected = "<a href='/'>< Back to Root</a></br>\r\n" +
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

    @Test
    public void testGetLinkStringNestedFolder() throws DirectoryNotFoundException {
        DirectoryHtmlCreator funCreator = new DirectoryHtmlCreator(journeyDirectory, rootDirectory);
        String expected = "<a href='/'>< Back to Root</a></br>\r\n" +
                "<a href='/journey/come'>come</a></br>\r\n";
        assertEquals(expected, funCreator.getLinkString());
    }

    @Test
    public void testGetLinkStringForNestedFolderInFunDirectory() throws DirectoryNotFoundException {
        Directory nestedDirectory = new Directory(journeyDirectory.getPathString() + "come/inside/");
        DirectoryHtmlCreator funCreator = new DirectoryHtmlCreator(nestedDirectory, rootDirectory);
        String expected = "<a href='/journey/come/inside/..'>< Back</a></br>\r\n" +
                "<a href='/journey/come/inside/and-find'>and-find</a></br>\r\n";
        assertEquals(expected, funCreator.getLinkString());
    }
}
