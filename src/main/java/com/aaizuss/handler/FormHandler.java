package com.aaizuss.handler;

import com.aaizuss.FormResource;
import com.aaizuss.Status;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import com.aaizuss.http.Response;

import java.util.ArrayList;

public class FormHandler implements Handler {
    private FormResource resource;

    public FormHandler(FormResource resource) {
        this.resource = resource;
    }

    @Override
    public Response execute(Request request) {
        if (!supportedMethod(request.getMethod())) {
            return new Response(Status.METHOD_NOT_ALLOWED);
        }
        Response response = new Response(Status.OK);
        response.setBody(getOrUpdateFormData(request));
        return response;
    }

    private String getOrUpdateFormData(Request request) {
        String method = request.getMethod();
        if (putOrPost(request)) {
            resource = new FormResource(request.getBody());
        } else if (method.equals(RequestMethods.DELETE)) {
            resource = new FormResource("");
        }
        return resource.getData();
    }

    private boolean putOrPost(Request request) {
        String method = request.getMethod();
        return method.equals(RequestMethods.POST) || method.equals(RequestMethods.PUT);
    }

    private boolean supportedMethod(String method) {
        ArrayList<String> methods = new ArrayList<>();
        methods.add(RequestMethods.GET);
        methods.add(RequestMethods.OPTIONS);
        methods.add(RequestMethods.POST);
        methods.add(RequestMethods.PUT);
        methods.add(RequestMethods.HEAD);
        methods.add(RequestMethods.DELETE);
        return methods.contains(method);
    }
}
