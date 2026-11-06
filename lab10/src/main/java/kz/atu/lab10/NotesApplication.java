package kz.atu.lab10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NotesApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(NotesApplication.class.getResource("notes-view.fxml"));
        stage.setScene(new Scene(loader.load(), 850, 550));
        stage.setTitle("Учебные конспекты — Менеджер заметок (Вариант 1)");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
