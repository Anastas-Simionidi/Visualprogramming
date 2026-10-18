package com.example.srovariant19;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/example/srovariant19/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1050, 680);
        stage.setTitle("Калькулятор питания — Food Nutrition Calculator (Вариант 19)");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(560);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}