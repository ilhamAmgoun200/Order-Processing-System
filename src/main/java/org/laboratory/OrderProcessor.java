package org.laboratory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {

    private static final long SLEEP_TIME = 360000; // 3 minutes en millisecondes

    // Méthode pour traiter les commandes dans un thread
    public void startProcessingPeriodically(String inputFile, String outputFile, String errorFile) {
        // Lancer le thread pour traiter les commandes toutes les 3 minutes
        Thread orderProcessingThread = new Thread(() -> {
            while (true) {
                processOrders(inputFile, outputFile, errorFile);
                try {
                    // Attendre 3 minutes avant de relancer le traitement
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    System.err.println("Erreur de pause du thread : " + e.getMessage());
                }
            }
        });
        orderProcessingThread.start(); // Démarre le thread qui tourne indéfiniment
    }

    public void processOrders(String inputFile, String outputFile, String errorFile) {
        // Lire les commandes à partir du fichier JSON
        List<Order> orders = JsonParser.readOrdersFromFile(inputFile);
        if (orders == null || orders.isEmpty()) {
            System.err.println("Aucune commande à traiter.");
            return;
        }

        List<Order> validOrders = new ArrayList<>();
        List<Order> invalidOrders = new ArrayList<>();

        for (Order order : orders) {
            if (CustomerChecker.doesCustomerExist(order.getCustomerId())) {
                insertOrderIntoDatabase(order);
                validOrders.add(order);
            } else {
                invalidOrders.add(order);
            }
        }

        // Ajouter les ordres valides et invalides aux fichiers respectifs
        OutputWriter.appendToOutputFile(outputFile, validOrders);
        OutputWriter.appendToOutputFile(errorFile, invalidOrders);

        // Vider le fichier d'entrée après le traitement
        clearInputFile(inputFile);
    }

    private void insertOrderIntoDatabase(Order order) {
        String query = "INSERT INTO `order` (id, date, amount, customer_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getId());
            stmt.setString(2, order.getDate());
            stmt.setDouble(3, order.getAmount());
            stmt.setInt(4, order.getCustomerId());
            stmt.executeUpdate();
            System.out.println("Ordre inséré : " + order.getId());
        } catch (Exception e) {
            System.err.println("Erreur d'insertion : " + e.getMessage());
        }
    }

    private void clearInputFile(String inputFile) {
        try {
            // Écrire une liste vide dans le fichier input.json pour le vider
            OutputWriter.writeEmptyFile(inputFile);
            System.out.println("Fichier d'entrée vidé : " + inputFile);
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage du fichier d'entrée : " + e.getMessage());
        }
    }
}
