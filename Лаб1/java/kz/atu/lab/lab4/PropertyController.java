package java.kz.atu.lab.lab4;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class PropertyController {
    @FXML
    private Label lblDisplay;
    @FXML
    private Label lblStatus;
    @FXML
    private ColorPicker colorPicker;

    private int textIndex = 0;
    private final String[] texts = {"Это тестовая надпись", "JavaFX удивителен!", "Свойства компонентов", "Программирование — это круто!"};

    @FXML
    protected void onChangeTextClick() {
        textIndex = (textIndex + 1) % texts.length;
        lblDisplay.setText(texts[textIndex]);
        updateStatus("Текст изменён");
    }

    @FXML
    protected void onToggleVisibilityClick() {
        boolean isVisible = lblDisplay.isVisible();
        lblDisplay.setVisible(!isVisible);
        updateStatus(isVisible ? "Надпись скрыта" : "Надпись показана");
    }

    @FXML
    protected void onToggleDisabledClick() {
        boolean isDisabled = lblDisplay.isDisable();
        lblDisplay.setDisable(!isDisabled);
        updateStatus(isDisabled ? "Надпись доступна" : "Надпись недоступна");
        lblDisplay.setStyle(lblDisplay.isDisable() ? "-fx-opacity: 0.5;" : "-fx-opacity: 1.0;");
    }

    @FXML
    protected void onResetStyleClick() {
        lblDisplay.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        lblDisplay.setTextFill(Color.BLACK);
        updateStatus("Стиль сброшен");
    }

    @FXML
    protected void onApplyColorClick() {
        Color color = colorPicker.getValue();
        lblDisplay.setTextFill(color);
        updateStatus("Цвет изменён");
    }

    @FXML
    protected void onColorChange() {
        // Необязательно: можно применять цвет сразу при выборе
    }

    private void updateStatus(String message) {
        lblStatus.setText("Статус: " + message);
    }
}