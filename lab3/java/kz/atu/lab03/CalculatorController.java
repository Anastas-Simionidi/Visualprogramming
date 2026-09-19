package kz.atu.lab03;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField txtNumber1;

    @FXML
    private TextField txtNumber2;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblOperation;

    @FXML
    private Label lblCounter;

    private int operationCount = 0;

    /**
     * Общий обработчик действий для всех арифметических кнопок
     */
    @FXML
    private void onOperation(ActionEvent event) {
        // Проверка на пустые поля (исправлено: работает на всех версиях Java)
        if (txtNumber1.getText().trim().isEmpty() || txtNumber2.getText().trim().isEmpty()) {
            showError("Введите оба числа.");
            return;
        }

        try {
            double number1 = Double.parseDouble(txtNumber1.getText().trim());
            double number2 = Double.parseDouble(txtNumber2.getText().trim());

            Button button = (Button) event.getSource();
            String operation = button.getText();

            double result = 0;

            switch (operation) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "x":
                    result = number1 * number2;
                    break;
                case "÷":
                    if (number2 == 0) {
                        showError("Деление на ноль невозможно.");
                        return;
                    }
                    result = number1 / number2;
                    break;
                case "x^y":
                    result = Math.pow(number1, number2);
                    break;
                case "%":
                    if (number2 == 0) {
                        showError("Деление на ноль невозможно.");
                        return;
                    }
                    result = number1 % number2;
                    break;
                default:
                    return;
            }

            lblResult.setText(String.format("Результат: %.2f", result));
            lblOperation.setText("Операция: " + operation);

            operationCount++;
            lblCounter.setText("Выполнено операций: " + operationCount);

        } catch (NumberFormatException e) {
            showError("Введите корректные числа.");
        }
    }

    /**
     * Очистка формы
     */
    @FXML
    private void onClearClick() {
        txtNumber1.clear();
        txtNumber2.clear();
        lblResult.setText("Результат: 0");
        lblOperation.setText("Операция: -");
        txtNumber1.requestFocus();
    }

    /**
     * Выход из приложения
     */
    @FXML
    private void onExitClick() {
        Platform.exit();
    }

    /**
     * Блокировка полей ввода
     */
    @FXML
    private void onLockClick() {
        txtNumber1.setDisable(true);
        txtNumber2.setDisable(true);
    }

    /**
     * Разблокировка полей ввода
     */
    @FXML
    private void onUnlockClick() {
        txtNumber1.setDisable(false);
        txtNumber2.setDisable(false);
        txtNumber1.requestFocus();
    }

    /**
     * Обработка наведения мыши
     */
    @FXML
    private void onMouseEntered() {
        lblOperation.setText("Выберите операцию");
    }

    /**
     * Обработка ухода мыши
     */
    @FXML
    private void onMouseExited() {
        if (lblResult.getText().equals("Результат: 0")) {
            lblOperation.setText("Операция: -");
        }
    }

    /**
     * Окно ошибки
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}