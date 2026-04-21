package org.example;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseConnectionTest {

    @Test
    void getConnection_throwsSQLException_whenPasswordMissing() {
        SQLException exception = assertThrows(SQLException.class, DatabaseConnection::getConnection);

        assertEquals(
                "Database password is missing. Set DB_PASS environment variable.",
                exception.getMessage()
        );
    }
}