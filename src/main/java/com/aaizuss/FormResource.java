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
        return data;
    }

    public void appendData(String newData) {
        this.data = getData() + newData;
    }

    public void overwriteData(String newData) {
        this.data = newData;
    }

    public void deleteData() {
        this.data = "";
    }
}
