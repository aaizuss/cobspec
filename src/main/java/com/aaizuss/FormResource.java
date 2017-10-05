package com.aaizuss;

public class FormResource {
    private String data;

    public FormResource() {
        this.data = "";
    }

    public FormResource(String data) {
        this.data = data;
    }

    public String getData() {
        System.out.println("data is " + data);
        return data;
    }
}
