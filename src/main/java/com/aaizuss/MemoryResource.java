package com.aaizuss;

public class MemoryResource {
    private String data;

    public MemoryResource() {
        this.data = "";
    }

    public MemoryResource(String data) {
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
