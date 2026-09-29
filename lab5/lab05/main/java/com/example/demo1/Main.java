package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загрузка файла строго из пакета com.example.demo1
        FXMLLoader loader = new FXMLLoader(getClass().getResource("questionnaire-view.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Анкета студента - Вариант 1");
        primaryStage.setScene(new Scene(root, 420, 540));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}