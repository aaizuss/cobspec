package com.aaizuss.handler;

import com.aaizuss.http.*;

public abstract class ContentHandler implements Handler {

    @Override
    public Response execute(Request request) {
        switch (request.getMethod()) {
            case RequestMethods.GET:
                return get(request);
            case RequestMethods.HEAD:
                return head(request);
            case RequestMethods.POST:
                return post(request);
            case RequestMethods.PUT:
                return post(request);
            case RequestMethods.OPTIONS:
                return options(request);
            case RequestMethods.PATCH:
                return patch(request);
            default: return new Response(Status.METHOD_NOT_ALLOWED);
        }
    }

    protected Response get(Request request) {
        return new Response(Status.METHOD_NOT_ALLOWED);
    }

    protected Response head(Request request) {
        return new Response(Status.METHOD_NOT_ALLOWED);
    }

    protected Response post(Request request) {
        return new Response(Status.METHOD_NOT_ALLOWED);
    }

    protected Response options(Request request) {
        Response response = new Response(Status.OK);
        response.setHeader(Header.ALLOW, allowedMethods());
        return response;
    }

    protected Response patch(Request request) {
        return new Response(Status.METHOD_NOT_ALLOWED);
    }

    protected abstract String allowedMethods();

}
