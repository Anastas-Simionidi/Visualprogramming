package java.kz.atu.lab.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GreetingApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kz/atu/lab/lab3/greeting-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 400);
        stage.setTitle("Приветствие");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}