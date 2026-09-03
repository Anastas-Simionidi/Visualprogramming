package java.kz.atu.lab.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AgeApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kz/atu/lab/lab6/age-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("Расчёт возраста");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}