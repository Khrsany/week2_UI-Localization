package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CalculationServiceTest {

    @Test
    void saveCalculation_handlesSQLException_whenDatabaseConnectionFails() {
        CalculationService service = new CalculationService();

        assertDoesNotThrow(() ->
                service.saveCalculation(100.0, 6.5, 1.8, 6.5, 11.7, "en")
        );
    }
}