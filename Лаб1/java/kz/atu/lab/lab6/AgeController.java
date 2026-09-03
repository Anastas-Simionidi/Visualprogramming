package java.kz.atu.lab.lab6;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AgeController {
    @FXML
    private TextField txtBirthYear;
    @FXML
    private Label lblResult;

    @FXML
    protected void onCalculateClick() {
        String input = txtBirthYear.getText().trim();

        // Проверка на пустое поле
        if (input.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ошибка ввода", "Поле не заполнено", "Пожалуйста, введите год рождения.");
            return;
        }

        try {
            int year = Integer.parseInt(input);

            // Проверка на корректность года
            int currentYear = LocalDate.now().getYear();
            if (year < 1900 || year > currentYear) {
                showAlert(Alert.AlertType.ERROR, "Некорректный год", 
                    "Год рождения должен быть между 1900 и " + currentYear,
                    "Пожалуйста, введите корректный год рождения.");
                return;
            }

            int age = currentYear - year;
            lblResult.setText("Ваш возраст: " + age + " лет");
            lblResult.setStyle("-fx-text-fill: #27ae60;");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка ввода", "Введён нечисловой формат", 
                "Пожалуйста, введите год рождения цифрами.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}