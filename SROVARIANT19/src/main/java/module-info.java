module com.example.srovariant19 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.srovariant19 to javafx.fxml;
    exports com.example.srovariant19;
}