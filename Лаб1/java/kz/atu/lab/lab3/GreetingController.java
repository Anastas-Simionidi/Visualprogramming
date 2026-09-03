package java.kz.atu.lab.lab3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GreetingController {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private Label lblResult;

    @FXML
    protected void onGreetClick() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();

        if (firstName.isEmpty() && lastName.isEmpty()) {
            lblResult.setText("Пожалуйста, введите имя и фамилию!");
            lblResult.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        String greeting = "Приветствуем вас, " + firstName + " " + lastName + "!";
        lblResult.setText(greeting);
        lblResult.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 18px;");
    }
}