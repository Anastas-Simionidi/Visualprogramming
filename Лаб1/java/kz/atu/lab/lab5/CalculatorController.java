package java.kz.atu.lab.lab5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    private TextField txtNum1;
    @FXML
    private TextField txtNum2;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblError;

    @FXML
    protected void onAdd() {
        calculate((a, b) -> a + b);
    }

    @FXML
    protected void onSubtract() {
        calculate((a, b) -> a - b);
    }

    @FXML
    protected void onMultiply() {
        calculate((a, b) -> a * b);
    }

    @FXML
    protected void onDivide() {
        calculate((a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Деление на ноль!");
            }
            return a / b;
        });
    }

    @FXML
    protected void onClear() {
        txtNum1.clear();
        txtNum2.clear();
        lblResult.setText("");
        lblError.setText("");
        lblResult.setStyle("-fx-text-fill: #2c3e50;");
    }

    private void calculate(Operation operation) {
        lblError.setText("");
        String num1Text = txtNum1.getText().trim();
        String num2Text = txtNum2.getText().trim();

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            lblError.setText("Пожалуйста, введите оба числа!");
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Text);
            double num2 = Double.parseDouble(num2Text);
            double result = operation.apply(num1, num2);
            lblResult.setText(String.format("%.2f", result));
            lblResult.setStyle("-fx-text-fill: #27ae60;");
        } catch (NumberFormatException e) {
            lblError.setText("Пожалуйста, введите корректные числа!");
        } catch (ArithmeticException e) {
            lblError.setText(e.getMessage());
        }
    }

    @FunctionalInterface
    private interface Operation {
        double apply(double a, double b);
    }
}