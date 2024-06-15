package org.example;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HttpTestingTest {

    private HttpTesting httpTesting;
    private HttpClient httpClient;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    void setup() {
        mockResponse = mock(HttpResponse.class);
    }

    @Test
    public void testGetTranscriptionResult() throws IOException, URISyntaxException, InterruptedException {

        String mockResponseBody = "{\"id\":\"testId\",\"status\":\"completed\",\"text\":\"This is a test transcript\"}";
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponse.statusCode()).thenReturn(200);
        // Set up the mock HttpClient to return the mock response

        // Set up the Gson object to deserialize the mock response body
        Gson gson = new Gson();

        // Set up the input Transcript object
        Transcript inputTranscript = new Transcript();
        inputTranscript.setAudio_url("https://example.com/test.mp3");
        inputTranscript.setId("123");
        httpClient = spy(HttpClient.class);
        httpTesting = new HttpTesting(httpClient,"testKey");
        doReturn(mockResponse).when(httpClient).send(any(),any());

        // Call the getTranscriptionResult method and check the result
        String result = httpTesting.getTranscriptionResult(inputTranscript, gson);
        assertEquals("This is a test transcript", result);
    }

}