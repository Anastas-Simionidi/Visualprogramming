package java.kz.atu.lab.lab4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PropertyApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kz/atu/lab/lab4/property-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 350);
        stage.setTitle("Свойства компонентов");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}