package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //ohjelman oletuskieli käynistykses
        Locale.setDefault(new Locale("en", "US"));

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("calculator-view.fxml")
        );

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("Fuel Calculator - Alabass");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}