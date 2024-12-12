package org.laboratory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputWriter {

    // Méthode pour ajouter des ordres au fichier de sortie (existing file append mode)
    public static void appendToOutputFile(String fileName, List<Order> orders) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Lire le contenu existant du fichier
            List<Order> existingOrders = mapper.readValue(new File(fileName), new TypeReference<List<Order>>() {});
            existingOrders.addAll(orders); // Ajouter les nouveaux ordres aux anciens
            mapper.writeValue(new File(fileName), existingOrders); // Réécrire le fichier avec les données mises à jour
            System.out.println("Fichier ajouté : " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    // Méthode pour vider le fichier (écrire une liste vide dans le fichier)
    public static void writeEmptyFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Écrire une liste vide dans le fichier
            mapper.writeValue(new File(fileName), new ArrayList<>());
            System.out.println("Fichier vidé : " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture JSON : " + e.getMessage());
        }
    }
}
