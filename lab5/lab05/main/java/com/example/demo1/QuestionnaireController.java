package com.example.demo1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class QuestionnaireController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtGroup;

    @FXML private ToggleGroup courseGroup;
    @FXML private ToggleGroup studyFormGroup;

    @FXML private CheckBox chkDormitory;
    @FXML private CheckBox chkScholarship;
    @FXML private CheckBox chkActivist;

    @FXML private Label lblResult;

    @FXML
    private void onCreateClick() {
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String group = txtGroup.getText().trim();

        if (fullName.isBlank() || email.isBlank() || group.isBlank()) {
            showError("Заполните обязательные поля (ФИО, Email, Группа).");
            return;
        }

        if (!isEmailValid(email)) {
            showError("Некорректный формат Email! Проверьте наличие '@' и точки.");
            txtEmail.requestFocus();
            return;
        }

        RadioButton course = (RadioButton) courseGroup.getSelectedToggle();
        RadioButton form = (RadioButton) studyFormGroup.getSelectedToggle();
        if (course == null || form == null) {
            showError("Выберите курс и форму обучения.");
            return;
        }

        String extras = buildExtras();

        lblResult.setText(
                "Результат:\n" +
                        "Студент: " + fullName +
                        "\nEmail: " + email +
                        "\nГруппа: " + group +
                        "\nКурс: " + course.getText() +
                        "\nФорма обучения: " + form.getText() +
                        "\nДополнительно: " + extras
        );
    }

    private String buildExtras() {
        StringBuilder result = new StringBuilder();
        if (chkDormitory.isSelected()) result.append("общежитие; ");
        if (chkScholarship.isSelected()) result.append("стипендия; ");
        if (chkActivist.isSelected()) result.append("активист; ");

        if (result.length() == 0) return "не выбрано";
        return result.toString();
    }

    private boolean isEmailValid(String email) {
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onClearClick() {
        txtFullName.clear();
        txtEmail.clear();
        txtGroup.clear();
        if (courseGroup.getSelectedToggle() != null) {
            courseGroup.getSelectedToggle().setSelected(false);
        }
        if (studyFormGroup.getSelectedToggle() != null) {
            studyFormGroup.getSelectedToggle().setSelected(false);
        }
        chkDormitory.setSelected(false);
        chkScholarship.setSelected(false);
        chkActivist.setSelected(false);
        lblResult.setText("Результат:");
        txtFullName.requestFocus();
    }

    @FXML
    private void onExitClick() {
        Platform.exit();
    }
}
