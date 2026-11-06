module com.example.lab10 {
    requires javafx.controls;
    requires javafx.fxml;

    // Добавляем модули Jackson для работы с JSON
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    // Открываем пакет для JavaFX и Jackson, чтобы они могли читать и создавать объекты
    opens kz.atu.lab10 to javafx.fxml, com.fasterxml.jackson.databind;

    exports kz.atu.lab10;
}
