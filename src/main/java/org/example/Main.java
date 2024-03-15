package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Définir le fichier contenant les URL
        File file = new File("dir_list.txt");

        // Créer un pool d'exécution avec 4 threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Lire chaque ligne du fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Soumettre la tâche à l'exécuteur
                String finalLine = line;
                executorService.submit(() -> {
                    long startTime = System.currentTimeMillis();

                    // Tester l'URL
                    int statusCode = 0;
                    try {
                        statusCode = testUrl(finalLine);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    long endTime = System.currentTimeMillis();

                    long executionTime = endTime - startTime;

                    // Si le statut n'est pas 404
                    if (statusCode != 404) {
                        // Afficher l'URL, le temps d'exécution et "valide" dans la console
                        System.out.println(finalLine + " : " + executionTime + " ms" + " valide");
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Arrêter l'exécuteur
        executorService.shutdown();
    }

    private static int testUrl(String url) throws IOException {
        // Ouvrir une connexion à l'URL
        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:5000/"+url).openConnection();

        // Définir la méthode de requête
        connection.setRequestMethod("GET");

        // Obtenir le code de statut
        return connection.getResponseCode();
    }
    }