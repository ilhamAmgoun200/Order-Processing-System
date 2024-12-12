package org.laboratory;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\21265\\IdeaProjects\\devoirlibre\\src\\main\\resources\\data\\input.json";
        String outputFilePath = "C:\\Users\\21265\\IdeaProjects\\devoirlibre\\src\\main\\resources\\data\\output.json";
        String errorFilePath = "C:\\Users\\21265\\IdeaProjects\\devoirlibre\\src\\main\\resources\\data\\error.json";

        // Créer une instance de OrderProcessor et démarrer le traitement périodique
        OrderProcessor processor = new OrderProcessor();
        processor.startProcessingPeriodically(inputFilePath, outputFilePath, errorFilePath);  // Démarrer le traitement toutes les 3 minutes
    }
}
