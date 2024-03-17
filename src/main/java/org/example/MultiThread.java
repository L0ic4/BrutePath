package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    // The url to be tested
    private static final String URL = "http://127.0.0.1:5000/";

    // The word list
    private static final String FILE_NAME = "dir_list.txt";

    // The main method
    public static void main(String[] args) {
        System.out.println("======================= BRUTE PATH ========================");

        // save start time
        long startTime = System.currentTimeMillis();

        // init executorService that can dynamically create and manage threads based on
        // the workload
        ExecutorService executorService = Executors.newCachedThreadPool();

        // read the content of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            /*
             * Each line is submitted to the ExecutorService for processing by an available
             * thread.
             * As the CachedThreadPool can create new threads if necessary and reuse
             * existing ones, each line is processed as soon as a thread is available.
             */
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                executorService.submit(() -> testUrlAndPrint(startTime, finalLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    // the method to show available url by testing it
    private static void testUrlAndPrint(Long startTime, String line) {
        try {
            // save status code by sending get to the url
            int statusCode = testUrl(URL + line);

            // show available url
            if (statusCode != 404) {
                System.out.println("---------------------------------------------------");
                System.out.println("find : " + URL + line);

                // show execution time
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("execution time: " + executionTime + " ms");
                System.out.println("---------------------------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Error checking URL: " + URL + line);
            e.printStackTrace();
        }
    }

    // The method to send get request in one url
    private static int testUrl(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Connection", "keep-alive"); // Using persistent HTTP connections
        return connection.getResponseCode();
    }
}