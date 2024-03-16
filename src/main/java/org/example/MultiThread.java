package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MultiThread {
    // the url to be tested
    private static final String URL = "http://127.0.0.1:5000/";

    //the word list
    private static final String FILE_NAME = "dir_list.txt";

    static Logger logger = Logger.getLogger(MultiThread.class.getName());

    public static void main(String[] args) {
       

        logger.info("======================= BRUTE PATH ========================");
        // save script start time
        long startTime = System.currentTimeMillis();

        //init cached thread pool (dynamically managed thread)
        ExecutorService executorService = Executors.newCachedThreadPool();

        //read the content of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            //test each line in cached thread pool
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
            int statusCode = testUrl(URL + line);
            if (statusCode != 404) {
                logger.info("---------------------------------------------------");
                logger.info("find : " + URL + line);
                long endTime = System.currentTimeMillis();

                long executionTime = endTime - startTime;

                logger.info("Temps d'exécution: " + executionTime + " millisecondes");
                logger.info("---------------------------------------------------");
            }
        } catch (IOException e) {
            logger.warning("Error checking URL: " + URL + line);
            e.printStackTrace();
        }
    }

    private static int testUrl(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Connection", "keep-alive"); // Using persistent HTTP connections
        return connection.getResponseCode();
    }
}