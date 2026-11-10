module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.model to javafx.base; // Разрешает доступ JavaFX к свойствам модели
    exports com.example.demo;
}
