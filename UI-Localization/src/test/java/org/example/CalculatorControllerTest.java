package org.example;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CalculatorControllerTest {

    private CalculatorController controller;
    private LocalizationService localizationService;
    private CalculationService calculationService;

    @BeforeAll
    static void initJavaFx() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new CalculatorController();

        localizationService = mock(LocalizationService.class);
        calculationService = mock(CalculationService.class);

        when(localizationService.getString("distance.label")).thenReturn("Distance");
        when(localizationService.getString("consumption.label")).thenReturn("Consumption");
        when(localizationService.getString("price.label")).thenReturn("Price");
        when(localizationService.getString("calculate.button")).thenReturn("Calculate");
        when(localizationService.getString("result.placeholder")).thenReturn("Result will appear here");
        when(localizationService.getString("invalid.input")).thenReturn("Invalid input");
        when(localizationService.getString("result.label")).thenReturn("Fuel needed: {0}, Total cost: {1}");

        setField("lblDistance", new Label());
        setField("lblConsumption", new Label());
        setField("lblPrice", new Label());
        setField("lblResult", new Label());

        setField("txtDistance", new TextField());
        setField("txtConsumption", new TextField());
        setField("txtPrice", new TextField());

        setField("btnCalculate", new Button());
        setField("btnEN", new Button());
        setField("btnFR", new Button());
        setField("btnJP", new Button());
        setField("btnIR", new Button());

        setField("localizationService", localizationService);
        setField("calculationService", calculationService);
    }

    @Test
    void initialize_setsEnglishTexts() throws Exception {
        controller.initialize();

        Label lblDistance = getField("lblDistance", Label.class);
        Label lblConsumption = getField("lblConsumption", Label.class);
        Label lblPrice = getField("lblPrice", Label.class);
        Label lblResult = getField("lblResult", Label.class);
        Button btnCalculate = getField("btnCalculate", Button.class);

        assertEquals("Distance", lblDistance.getText());
        assertEquals("Consumption", lblConsumption.getText());
        assertEquals("Price", lblPrice.getText());
        assertEquals("Calculate", btnCalculate.getText());
        assertEquals("Result will appear here", lblResult.getText());

        verify(localizationService).loadStrings("en");
    }

    @Test
    void calculate_validInput_setsResultAndSavesCalculation() throws Exception {
        controller.initialize();

        TextField txtDistance = getField("txtDistance", TextField.class);
        TextField txtConsumption = getField("txtConsumption", TextField.class);
        TextField txtPrice = getField("txtPrice", TextField.class);
        Label lblResult = getField("lblResult", Label.class);

        txtDistance.setText("200");
        txtConsumption.setText("6.5");
        txtPrice.setText("1.8");

        Method calculateMethod = CalculatorController.class.getDeclaredMethod("calculate", ActionEvent.class);
        calculateMethod.setAccessible(true);
        calculateMethod.invoke(controller, new ActionEvent());

        assertEquals("Fuel needed: 13, Total cost: 23.4", lblResult.getText());

        ArgumentCaptor<Double> distanceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> consumptionCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> totalFuelCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> totalCostCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<String> languageCaptor = ArgumentCaptor.forClass(String.class);

        verify(calculationService).saveCalculation(
                distanceCaptor.capture(),
                consumptionCaptor.capture(),
                priceCaptor.capture(),
                totalFuelCaptor.capture(),
                totalCostCaptor.capture(),
                languageCaptor.capture()
        );

        assertEquals(200.0, distanceCaptor.getValue());
        assertEquals(6.5, consumptionCaptor.getValue());
        assertEquals(1.8, priceCaptor.getValue());
        assertEquals(13.0, totalFuelCaptor.getValue());
        assertEquals(23.4, totalCostCaptor.getValue(), 0.0001);
        assertEquals("en", languageCaptor.getValue());
    }

    @Test
    void calculate_invalidInput_showsErrorMessage() throws Exception {
        controller.initialize();

        TextField txtDistance = getField("txtDistance", TextField.class);
        TextField txtConsumption = getField("txtConsumption", TextField.class);
        TextField txtPrice = getField("txtPrice", TextField.class);
        Label lblResult = getField("lblResult", Label.class);

        txtDistance.setText("abc");
        txtConsumption.setText("6.5");
        txtPrice.setText("1.8");

        Method calculateMethod = CalculatorController.class.getDeclaredMethod("calculate", ActionEvent.class);
        calculateMethod.setAccessible(true);
        calculateMethod.invoke(controller, new ActionEvent());

        assertEquals("Invalid input", lblResult.getText());
        verify(calculationService, never()).saveCalculation(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString());
    }

    @Test
    void switchToFrench_loadsFrenchLanguage() throws Exception {
        controller.initialize();

        Method switchMethod = CalculatorController.class.getDeclaredMethod("switchToFrench", ActionEvent.class);
        switchMethod.setAccessible(true);
        switchMethod.invoke(controller, new ActionEvent());

        verify(localizationService).loadStrings("fr");
    }

    @Test
    void switchToJapanese_loadsJapaneseLanguage() throws Exception {
        controller.initialize();

        Method switchMethod = CalculatorController.class.getDeclaredMethod("switchToJapanese", ActionEvent.class);
        switchMethod.setAccessible(true);
        switchMethod.invoke(controller, new ActionEvent());

        verify(localizationService).loadStrings("ja");
    }

    @Test
    void switchToPersian_loadsPersianLanguage() throws Exception {
        controller.initialize();

        Method switchMethod = CalculatorController.class.getDeclaredMethod("switchToPersian", ActionEvent.class);
        switchMethod.setAccessible(true);
        switchMethod.invoke(controller, new ActionEvent());

        verify(localizationService).loadStrings("fa");
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = CalculatorController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private <T> T getField(String fieldName, Class<T> type) throws Exception {
        Field field = CalculatorController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return type.cast(field.get(controller));
    }
}