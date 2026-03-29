package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalculatorController {

    @FXML
    private Label lblDistance;

    @FXML
    private Label lblConsumption;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblResult;

    @FXML
    private TextField txtDistance;

    @FXML
    private TextField txtConsumption;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnEN;

    @FXML
    private Button btnFR;

    @FXML
    private Button btnJP;

    @FXML
    private Button btnIR;

    private ResourceBundle bundle;

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

            String resultText = MessageFormat.format(
                    bundle.getString("result.label"),
                    totalFuel,
                    totalCost
            );

            lblResult.setText(resultText);

        } catch (Exception e) {
            lblResult.setText(bundle.getString("invalid.input"));
        }
    }

    @FXML
    private void switchToEnglish(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    private void switchToFrench(ActionEvent event) {
        setLanguage(new Locale("fr", "FR"));
    }

    @FXML
    private void switchToJapanese(ActionEvent event) {
        setLanguage(new Locale("ja", "JP"));
    }

    @FXML
    private void switchToPersian(ActionEvent event) {
        setLanguage(new Locale("fa", "IR"));
    }

    private void setLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("org.example.messages", locale);

        lblDistance.setText(bundle.getString("distance.label"));
        lblConsumption.setText(bundle.getString("consumption.label"));
        lblPrice.setText(bundle.getString("price.label"));
        btnCalculate.setText(bundle.getString("calculate.button"));
        lblResult.setText(bundle.getString("result.placeholder"));
    }
}