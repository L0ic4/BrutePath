package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {

        String url = "http://127.0.0.1:5000/";
        String filename = "dir_list.txt";

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                executorService.submit(() -> testUrlAndPrint(finalLine, url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    private static void testUrlAndPrint(String line, String baseURL) {
        try {
            int statusCode = testUrl(baseURL + line);
            if (statusCode != 404) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error checking URL: " + baseURL + line);
            e.printStackTrace();
        }
    }

    private static int testUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection.getResponseCode();
    }
}