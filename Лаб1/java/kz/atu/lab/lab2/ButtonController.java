package java.kz.atu.lab.lab2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ButtonController {
    @FXML
    private Label lblOutput;
    @FXML
    private Button btnGreeting;
    @FXML
    private Button btnInfo;
    @FXML
    private Button btnExit;

    @FXML
    protected void onGreetingClick() {
        lblOutput.setText("Привет! Добро пожаловать в JavaFX!");
        lblOutput.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 20px;");
    }

    @FXML
    protected void onInfoClick() {
        lblOutput.setText("Это приложение демонстрирует обработку событий кнопок.");
        lblOutput.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 18px;");
    }

    @FXML
    protected void onExitClick() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}