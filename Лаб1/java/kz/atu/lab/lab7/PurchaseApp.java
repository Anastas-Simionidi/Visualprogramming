package java.kz.atu.lab.lab7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PurchaseApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kz/atu/lab/lab7/purchase-view.fxml"));
        Scene scene = new Scene(loader.load(), 450, 400);
        stage.setTitle("Расчёт стоимости покупки");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}