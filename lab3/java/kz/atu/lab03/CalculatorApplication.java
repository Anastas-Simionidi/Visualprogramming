package kz.atu.lab03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Указываем абсолютный путь от папки resources
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("/kz/atu/lab03/calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 480);
        stage.setTitle("Интерактивный калькулятор");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}