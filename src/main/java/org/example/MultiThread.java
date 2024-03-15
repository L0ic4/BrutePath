package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    // the url to be tested
    private static final String url = "http://127.0.0.1:5000/";

    //the word list
    private static final String filename = "dir_list.txt";

    public static void main(String[] args) {
        // save script start time
        long startTime = System.currentTimeMillis();

        //init cached thread pool (dynamically manage thread)
        ExecutorService executorService = Executors.newCachedThreadPool();

        //read the content of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            //test each line in thread pool
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                executorService.submit(() -> testUrlAndPrint(startTime, finalLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }


    private static void testUrlAndPrint(Long startTime, String line) {
        try {
            int statusCode = testUrl(MultiThread.url + line);
            if (statusCode != 404) {
                System.out.println("find : " + url + line);
                long endTime = System.currentTimeMillis();

                long executionTime = endTime - startTime;

                System.out.println("Temps d'exécution: " + executionTime + " millisecondes");
            }
        } catch (IOException e) {
            System.err.println("Error checking URL: " + MultiThread.url + line);
            e.printStackTrace();
        }
    }

    private static int testUrl(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Connection", "keep-alive"); // Définir la connexion comme persistante
        return connection.getResponseCode();
    }
}