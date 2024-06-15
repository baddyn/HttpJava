package org.example;

import com.google.gson.Gson;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3");

        Gson gson = new Gson();
        String jsonRequest =  gson.toJson(transcript);

        System.out.println(jsonRequest);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpTesting httpTesting = new HttpTesting(httpClient, Constants.API_KEY);
        try{
            String result = httpTesting.getTranscriptionResult(transcript, gson);
            System.out.println(result);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}