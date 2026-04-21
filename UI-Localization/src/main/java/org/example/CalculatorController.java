package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.text.MessageFormat;
import java.util.Locale;

public class CalculatorController {

    @FXML private Label lblDistance, lblConsumption, lblPrice, lblResult;
    @FXML private TextField txtDistance, txtConsumption, txtPrice;
    @FXML private Button btnCalculate, btnEN, btnFR, btnJP, btnIR;

    private LocalizationService localizationService = new LocalizationService();
    private CalculationService calculationService = new CalculationService();
    private String currentLanguage = "en";

    @FXML
    public void initialize() {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    private void calculate(ActionEvent event) {
        try {
            double distance = Double.parseDouble(txtDistance.getText());
            double consumption = Double.parseDouble(txtConsumption.getText());
            double price = Double.parseDouble(txtPrice.getText());

            double totalFuel = (consumption / 100.0) * distance;
            double totalCost = totalFuel * price;

            String resultTemplate = localizationService.getString("result.label");
            String resultText = MessageFormat.format(resultTemplate, totalFuel, totalCost);
            lblResult.setText(resultText);

            calculationService.saveCalculation(distance, consumption, price, totalFuel, totalCost, currentLanguage);

        } catch (Exception e) {
            lblResult.setText(localizationService.getString("invalid.input"));
        }
    }

    @FXML private void switchToEnglish(ActionEvent event) { setLanguage(new Locale("en", "US")); }
    @FXML private void switchToFrench(ActionEvent event) { setLanguage(new Locale("fr", "FR")); }
    @FXML private void switchToJapanese(ActionEvent event) { setLanguage(new Locale("ja", "JP")); }
    @FXML private void switchToPersian(ActionEvent event) { setLanguage(new Locale("fa", "IR")); }

    private void setLanguage(Locale locale) {
        currentLanguage = locale.getLanguage();
        localizationService.loadStrings(currentLanguage);

        lblDistance.setText(localizationService.getString("distance.label"));
        lblConsumption.setText(localizationService.getString("consumption.label"));
        lblPrice.setText(localizationService.getString("price.label"));
        btnCalculate.setText(localizationService.getString("calculate.button"));
        lblResult.setText(localizationService.getString("result.placeholder"));
    }
}