package com.aaizuss.handler;

import com.aaizuss.datastore.MockDirectory;
import com.aaizuss.http.Request;
import com.aaizuss.http.RequestMethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestLoggerTest {

    private MockDirectory mockDirectory;
    private RequestLogger logger;
    private Request request;

    @Test
    public void givenEmptyDirectoryLoggerCreatesLogFile() {
        mockDirectory = MockDirectory.emptyDirectory();
        logger = new RequestLogger(mockDirectory);

        request = new Request(RequestMethods.PUT, "/stuff");
        logger.logRequest(request);

        assertTrue(mockDirectory.containsResource("/logs"));
    }

    @Test
    public void givenDirectoryWithLogsFileLoggerAddsToIt() {
        mockDirectory = MockDirectory.withTextFile("logs", "GET /log HTTP/1.1");
        logger = new RequestLogger(mockDirectory);

        request = new Request(RequestMethods.PUT, "/stuff");
        logger.logRequest(request);

        assertTrue(mockDirectory.containsResource("/logs"));
        assertEquals("GET /log HTTP/1.1\nPUT /stuff HTTP/1.1", logger.getLogs());
    }

    @Test
    public void testClearLogs() {
        mockDirectory = MockDirectory.withTextFile("logs", "GET /log HTTP/1.1");
        logger = new RequestLogger(mockDirectory);

        logger.clearLogs();
        assertEquals("", logger.getLogs());
    }

}
