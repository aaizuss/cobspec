package com.aaizuss.handler;

import com.aaizuss.datastore.DataStore;
import com.aaizuss.http.Request;

public class RequestLogger {
    private DataStore directory;

    public RequestLogger(DataStore directory) {
        this.directory = directory;
    }

    public void logRequest(Request request) {
        String entry = request.toString();
        directory.writeToResource("/logs", entry, true);
    }

    public void clearLogs() {
        directory.clearDataFromResource("/logs");
    }

    public String getLogs() {
        byte[] logContent = directory.read("/logs");
        return new String(logContent);
    }
}
