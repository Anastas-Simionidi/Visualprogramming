package kz.atu.lab04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LayoutApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Указываем абсолютный путь от корня ресурсов
        FXMLLoader fxmlLoader = new FXMLLoader(LayoutApplication.class.getResource("/kz/atu/lab04/layout-view.fxml"));

        if (fxmlLoader.getLocation() == null) {
            throw new RuntimeException("Не найден FXML-файл! Проверьте путь: src/main/resources/kz/atu/lab04/layout-view.fxml");
        }

        Scene scene = new Scene(fxmlLoader.load(), 850, 600);

        // Безопасное подключение CSS
        var cssUrl = getClass().getResource("/kz/atu/lab04/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setTitle("Панель управления студентами — Вариант 1");
        stage.setMinWidth(760);
        stage.setMinHeight(520);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}