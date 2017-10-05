package com.aaizuss;

import com.aaizuss.exception.DirectoryNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryHtmlCreatorTest {
    private DirectoryHtmlCreator htmlCreator;
    private Directory rootDirectory;
    private Directory funDirectory;
    private Directory puppiesDirectory;

    private void setUpPuppiesCreator() throws DirectoryNotFoundException {
        htmlCreator = new DirectoryHtmlCreator(puppiesDirectory, rootDirectory);
    }

    private void makePuppiesDirectory() throws DirectoryNotFoundException {
        puppiesDirectory = new Directory(TestConstants.TEST_DIR + "/puppies/");
    }

    private void makeFunDirectory() throws DirectoryNotFoundException {
        funDirectory = new Directory(TestConstants.USER_DIR + "/fun-stuff/");
    }

    private void makeRootDirectory() throws DirectoryNotFoundException {
        rootDirectory= new Directory(TestConstants.USER_DIR + "/test-directory/");
    }

    @Before
    public void setUp() throws DirectoryNotFoundException {
        makePuppiesDirectory();
        makeFunDirectory();
        makeRootDirectory();
        setUpPuppiesCreator();
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
                "<a href='/empty-folder'>empty-folder</a></br>\r\n" +
                        "<a href='/puppies'>puppies</a></br>\r\n" +
                        "<a href='/text-file.txt'>text-file.txt</a></br>\r\n";
        DirectoryHtmlCreator creator = new DirectoryHtmlCreator(rootDirectory, rootDirectory);
        assertEquals(expected, creator.getLinkString());
    }

    @Test
    public void testGetLinkStringNestedFolder() throws DirectoryNotFoundException {
        Directory nestedDirectory = new Directory(TestConstants.USER_DIR + "/fun-stuff/journey/");
        DirectoryHtmlCreator funCreator = new DirectoryHtmlCreator(nestedDirectory, funDirectory);
        String expected = "<a href='/'>< Back to Root</a></br>\r\n" +
                "<a href='/journey/come'>come</a></br>\r\n";
        assertEquals(expected, funCreator.getLinkString());
    }

    @Test
    public void testGetLinkStringForNestedFolderInFunDirectory() throws DirectoryNotFoundException {
        Directory nestedDirectory = new Directory(TestConstants.USER_DIR + "/fun-stuff/journey/come/inside/");
        DirectoryHtmlCreator funCreator = new DirectoryHtmlCreator(nestedDirectory, funDirectory);
        String expected = "<a href='/journey/come/inside/..'>< Back</a></br>\r\n" +
                "<a href='/journey/come/inside/and-find'>and-find</a></br>\r\n";
        assertEquals(expected, funCreator.getLinkString());
    }
}
