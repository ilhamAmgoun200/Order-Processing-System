package org.laboratory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class customerinserer {

    public static void insertCustomer(int id, String nom, String email, String phone) {
        String query = "INSERT INTO customer (id, nom, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, nom);
            statement.setString(3, email);
            statement.setString(4, phone);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client inséré avec succès : " + nom);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du client : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Ajout de clients
        insertCustomer(3, "Ilham Amgoun", "ilham.amg@example.com", "0658746932");
        insertCustomer(4, "Jane Smith", "jane.smith@example.com", "0671234567");
        insertCustomer(5, "Alice Johnson", "alice.johnson@example.com", "0677654321");
    }
}
