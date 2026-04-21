package org.example;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class CalculationServiceTest {

    @Test
    void saveCalculation_executesInsertSuccessfully() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement pstmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(pstmt);

        CalculationService service = new CalculationService() {
            @Override
            protected Connection getConnection() {
                return conn;
            }
        };

        service.saveCalculation(100.0, 6.5, 1.8, 6.5, 11.7, "en");

        verify(conn).prepareStatement(anyString());
        verify(pstmt).setDouble(1, 100.0);
        verify(pstmt).setDouble(2, 6.5);
        verify(pstmt).setDouble(3, 1.8);
        verify(pstmt).setDouble(4, 6.5);
        verify(pstmt).setDouble(5, 11.7);
        verify(pstmt).setString(6, "en");
        verify(pstmt).executeUpdate();
    }

    @Test
    void saveCalculation_handlesSQLException_whenDatabaseConnectionFails() {
        CalculationService service = new CalculationService() {
            @Override
            protected Connection getConnection() throws SQLException {
                throw new SQLException("Database password is missing. Set DB_PASS environment variable.");
            }
        };

        assertDoesNotThrow(() ->
                service.saveCalculation(100.0, 6.5, 1.8, 6.5, 11.7, "en")
        );
    }
}