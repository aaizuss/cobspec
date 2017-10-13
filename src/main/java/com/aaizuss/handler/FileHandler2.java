package com.aaizuss.handler;

import com.aaizuss.ResourceReader;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;

public class FileHandler2 implements Handler {

    private DataStore directory;

    public FileHandler2(DataStore directory) {
        this.directory = directory;
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response(Status.NOT_FOUND);

        if (directory.containsResource(request.getUri()))
        {
            if (ResourceReader.getContentType(request.getUri()).equals("text/plain"))
            {
                response = new Response(Status.OK);
                response.setHeader(Header.CONTENT_TYPE, "text/plain");
            } else
            {
                response = new Response(Status.OK);
                response.setHeader(Header.CONTENT_TYPE, "image/png");
            }
        }
        return response;
    }
}
