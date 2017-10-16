package com.aaizuss.handler;

import com.aaizuss.ResourceReader;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;

public class FileHandler2 implements Handler {

    private DataStore directory;
    private ResourceReader reader;

    public FileHandler2(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response(Status.NOT_FOUND);

        if (directory.containsResource(request.getUri())) {
            if (reader.getContentType(request.getUri()).equals("text/plain")) {
                response = new TextContentHandler(directory).execute(request);
            } else {
                response = new MediaContentHandler(directory).execute(request);
            }
        }
        return response;
    }
}
