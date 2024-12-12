package org.laboratory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerChecker {

    public static boolean doesCustomerExist(int customerId) {
        String query = "SELECT COUNT(*) FROM customer WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println("Erreur de vérification client : " + e.getMessage());
        }
        return false;
    }
}
