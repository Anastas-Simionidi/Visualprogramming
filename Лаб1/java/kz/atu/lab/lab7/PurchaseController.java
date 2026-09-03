package java.kz.atu.lab.lab7;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PurchaseController {
    @FXML
    private TextField txtProduct;
    @FXML
    private TextField txtPrice;
    @FXML
    private Spinner<Integer> spinnerQuantity;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblError;

    @FXML
    protected void onCalculateClick() {
        lblError.setText("");
        String product = txtProduct.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (product.isEmpty()) {
            lblError.setText("Пожалуйста, введите название товара.");
            return;
        }

        if (priceText.isEmpty()) {
            lblError.setText("Пожалуйста, введите цену товара.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                lblError.setText("Цена не может быть отрицательной.");
                return;
            }

            int quantity = spinnerQuantity.getValue();
            double total = price * quantity;

            lblResult.setText(String.format("%s: %.2f ₸ × %d шт. = %.2f ₸", product, price, quantity, total));
            lblResult.setStyle("-fx-text-fill: #27ae60;");

        } catch (NumberFormatException e) {
            lblError.setText("Пожалуйста, введите корректное числовое значение для цены.");
        }
    }
}