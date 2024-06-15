package org.example;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpTesting {

    private final HttpClient httpClient;
    private final String apiKey;

    public HttpTesting(HttpClient httpClient, String apiKey) {
        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    public Transcript getTranscript(Transcript transcript, Gson gson) throws URISyntaxException, IOException, InterruptedException {

        try{
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                    .timeout(Duration.ofMinutes(2))
                    .header("Authorization",apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(transcript)))
                    .build();

            HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            if(postResponse.statusCode() != 200) {
                throw new RuntimeException("Failed to create transcript");
            }

            System.out.println(postResponse.body());

            Transcript responseTranscript = gson.fromJson(postResponse.body(), Transcript.class);
            String transcriptId = responseTranscript.getId();
            String transcriptionStatus = responseTranscript.getStatus();

            while(!(transcriptionStatus.equals("completed")) && ! (transcriptionStatus.equals("error")) ) {
                Thread.sleep(2000);

                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(new URI("https://api.assemblyai.com/v2/transcript/" + transcriptId))
                        .timeout(Duration.ofMinutes(2))
                        .header("Authorization",apiKey)
                        .GET()
                        .build();

                HttpResponse<String> getResponse = httpClient.send(getRequest,HttpResponse.BodyHandlers.ofString());

                responseTranscript = gson.fromJson(getResponse.body(), Transcript.class);
                transcriptionStatus = responseTranscript.getStatus();
                System.out.println(getResponse.body());
            }

            if(responseTranscript.getStatus().equals("completed"))
                return responseTranscript;
            throw new RuntimeException("failed to get transcript");
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public String getTranscriptionResult(Transcript transcript, Gson gson) throws URISyntaxException, IOException, InterruptedException {
        Transcript responseTranscript = this.getTranscript(transcript, gson);
        return responseTranscript.getText();
    }
}


