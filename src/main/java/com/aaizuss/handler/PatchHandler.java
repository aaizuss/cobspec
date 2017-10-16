package com.aaizuss.handler;

import com.aaizuss.*;
import com.aaizuss.datastore.DataStore;
import com.aaizuss.encoder.ShaEncoder;
import com.aaizuss.http.Header;
import com.aaizuss.http.Request;
import com.aaizuss.http.Response;
import com.aaizuss.http.Status;

public class PatchHandler implements Handler {
    private DataStore directory;
    private ResourceReader reader;

    public PatchHandler(DataStore directory) {
        this.directory = directory;
        this.reader = directory.getResourceReader();
    }

    @Override
    public Response execute(Request request) {
        if (etagMatches(request)) {
            FileResourceWriter.updateResource(request.getUri(), directory, request.getBody(), false);
            return new Response(Status.NO_CONTENT);
        } else {
            return new Response(Status.PRECONDITION_FAILED);
        }
    }

    private boolean etagMatches(Request request) {
        String etag = request.getHeader(Header.IF_MATCH);
        String fileSha = ShaEncoder.encode(getFileContent(request));
        return etag.equals(fileSha);
    }

    private String getFileContent(Request request) {
        return new String(reader.getContent(request.getUri(), directory));
    }


}
