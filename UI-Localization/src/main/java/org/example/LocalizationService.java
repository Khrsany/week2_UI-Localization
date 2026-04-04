package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocalizationService {
    private Map<String, String> cache = new HashMap<>();
    private static final String SELECT_QUERY = "SELECT `key`, value FROM localization_strings WHERE language = ?";

    public void loadStrings(String language) {
        cache.clear();
        System.out.println("--- Ladataan kieltä: " + language + " ---");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_QUERY)) {

            pstmt.setString(1, language);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                String key = rs.getString("key");
                String value = rs.getString("value");
                cache.put(key, value);
                count++;
            }
            System.out.println("Lataus valmis. Löydetty " + count + " riviä.");

        } catch (SQLException e) {
            System.out.println("VIRHE TIETOKANTAYHTEYDESSÄ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return cache.getOrDefault(key, "TEXT_NOT_FOUND!");
    }

    public Set<String> getAllKeys() {
        return cache.keySet();
    }
}