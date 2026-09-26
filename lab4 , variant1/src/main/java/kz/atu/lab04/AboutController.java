package kz.atu.lab04;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
    @FXML private Button btnClose;

    @FXML
    private void onCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}