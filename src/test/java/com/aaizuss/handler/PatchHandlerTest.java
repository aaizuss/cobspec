package com.aaizuss.handler;

import com.aaizuss.*;
import com.aaizuss.exception.DirectoryNotFoundException;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PatchHandlerTest {

    private Request request;
    private PatchHandler handler;
    private Directory directory;
    private String etag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    private String uri = "/temp_file.txt";
    private Response response;


    public File createTempTxtFile(TemporaryFolder tempFolder) throws IOException {
        File createdFile = tempFolder.newFile("temp_file.txt");
        FileResourceWriter.updateResource(uri, directory, "default content", false);
        return createdFile;
    }

    @Rule
    public TemporaryFolder patchFolder = new TemporaryFolder();

    @Before
    public void setUp() throws DirectoryNotFoundException, IOException {
        directory = new Directory(patchFolder.getRoot().getPath());
        request = new Request("PATCH", uri);
        handler = new PatchHandler(directory);
        createTempTxtFile(patchFolder);
    }

    @Test
    public void testResponseStatusForSuccessfulPatch() {
        request.addHeader(Header.IF_MATCH, etag);
        request.setBody("different content");
        response = handler.execute(request);
        assertEquals(Status.NO_CONTENT, response.getStatus());
    }

    @Test
    public void testChangesContentWhenSuccessfulPatch() {
        request.addHeader(Header.IF_MATCH, etag);
        request.setBody("different content");
        response = handler.execute(request);

        String updatedContent = new String(ResourceReader.getContent(uri, directory));
        assertEquals("different content", updatedContent);

    }

    @Test
    public void testResponseStatusWhenNoMatch() {
        request.addHeader(Header.IF_MATCH, "nah");
        request.setBody("diferent content");
        response = handler.execute(request);
        assertEquals(Status.PRECONDITION_FAILED, response.getStatus());
    }

    @Test
    public void testDoesNotChangeContentWhenNoMatch() {
        request.addHeader(Header.IF_MATCH, "nah");
        request.setBody("different content");
        response = handler.execute(request);

        String updatedContent = new String(ResourceReader.getContent(uri, directory));
        assertEquals("default content", updatedContent);
    }
}
