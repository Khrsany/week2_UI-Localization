package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalculationService {
    private static final String INSERT_QUERY =
            "INSERT INTO calculation_records (distance, consumption, price, total_fuel, total_cost, language) VALUES (?, ?, ?, ?, ?, ?)";

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void saveCalculation(double distance, double consumption, double price, double totalFuel, double totalCost, String language) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {
            pstmt.setDouble(1, distance);
            pstmt.setDouble(2, consumption);
            pstmt.setDouble(3, price);
            pstmt.setDouble(4, totalFuel);
            pstmt.setDouble(5, totalCost);
            pstmt.setString(6, language);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}