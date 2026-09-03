package java.kz.atu.lab.lab8;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SurveyController {
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> comboCity;
    @FXML
    private RadioButton rbMale;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private ComboBox<String> comboCourse;
    @FXML
    private CheckBox chkStudent;
    @FXML
    private CheckBox chkEmployed;
    @FXML
    private CheckBox chkMarried;
    @FXML
    private Label lblResult;

    @FXML
    public void initialize() {
        comboCity.getItems().addAll("Алматы", "Астана", "Шымкент", "Караганда", "Актобе", "Другой");
        comboCourse.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс", "Магистратура");
        comboCity.setValue("Алматы");
        comboCourse.setValue("1 курс");
        rbMale.setSelected(true);
    }

    @FXML
    protected void onSubmitClick() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            showAlert("Ошибка", "Пожалуйста, введите ФИО.");
            return;
        }

        String city = comboCity.getValue();
        String gender = rbMale.isSelected() ? "Мужской" : "Женский";
        String course = comboCourse.getValue();

        StringBuilder extras = new StringBuilder();
        if (chkStudent.isSelected()) extras.append("Студент, ");
        if (chkEmployed.isSelected()) extras.append("Работает, ");
        if (chkMarried.isSelected()) extras.append("Женат/Замужем, ");
        
        String extraText = extras.length() > 0 ? extras.substring(0, extras.length() - 2) : "Не указано";

        String result = String.format(
            "ФИО: %s\nГород: %s\nПол: %s\nКурс: %s\nДополнительно: %s",
            name, city, gender, course, extraText
        );

        lblResult.setText(result);
        lblResult.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}