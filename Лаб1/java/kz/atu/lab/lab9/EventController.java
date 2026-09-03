package java.kz.atu.lab.lab9;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventController {
    @FXML
    private TextField txtInput;
    @FXML
    private Label lblEventInfo;
    @FXML
    private Label lblMouseInfo;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Обработка нажатия кнопки (onAction)
    @FXML
    protected void onButtonClick() {
        String text = txtInput.getText().trim();
        if (text.isEmpty()) {
            lblEventInfo.setText("Событие: Кнопка нажата, но поле пустое");
        } else {
            lblEventInfo.setText("Событие: Введён текст \"" + text + "\"");
        }
        lblEventInfo.setStyle("-fx-text-fill: #2980b9;");
    }

    // Обработка изменения текста в поле (onKeyReleased / onTextChange)
    @FXML
    protected void onTextChange() {
        String text = txtInput.getText();
        int length = text.length();
        lblEventInfo.setText("Событие: Изменение текста (длина: " + length + ")");
        lblEventInfo.setStyle("-fx-text-fill: #27ae60;");
    }

    // Обработка события мыши - наведение
    @FXML
    protected void onMouseEntered(MouseEvent event) {
        lblMouseInfo.setText("Мышь: Наведена на компонент (x=" + (int)event.getX() + ", y=" + (int)event.getY() + ")");
        lblMouseInfo.setStyle("-fx-text-fill: #e67e22;");
    }

    // Обработка события мыши - выход
    @FXML
    protected void onMouseExited(MouseEvent event) {
        lblMouseInfo.setText("Мышь: Покинула компонент");
        lblMouseInfo.setStyle("-fx-text-fill: #7f8c8d;");
    }

    // Обработка события мыши - клик
    @FXML
    protected void onMouseClicked(MouseEvent event) {
        String time = LocalDateTime.now().format(formatter);
        lblMouseInfo.setText("Мышь: Клик в " + time + " (x=" + (int)event.getX() + ", y=" + (int)event.getY() + ")");
        lblMouseInfo.setStyle("-fx-text-fill: #8e44ad;");
    }

    // Очистка информации
    @FXML
    protected void onClearClick() {
        txtInput.clear();
        lblEventInfo.setText("Информация о событиях будет здесь");
        lblEventInfo.setStyle("-fx-text-fill: #7f8c8d;");
        lblMouseInfo.setText("Информация о мыши будет здесь");
        lblMouseInfo.setStyle("-fx-text-fill: #7f8c8d;");
    }
}