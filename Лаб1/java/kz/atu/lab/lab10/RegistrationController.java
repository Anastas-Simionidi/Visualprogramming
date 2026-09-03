package java.kz.atu.lab.lab10;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationController {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private CheckBox chkAgree;
    @FXML
    private Label lblStatus;

    @FXML
    protected void onRegisterClick() {
        // Валидация имени
        String firstName = txtFirstName.getText().trim();
        if (firstName.isEmpty()) {
            showAlert("Ошибка валидации", "Пожалуйста, введите имя.");
            return;
        }

        // Валидация фамилии
        String lastName = txtLastName.getText().trim();
        if (lastName.isEmpty()) {
            showAlert("Ошибка валидации", "Пожалуйста, введите фамилию.");
            return;
        }

        // Валидация email
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            showAlert("Ошибка валидации", "Пожалуйста, введите email.");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            showAlert("Ошибка валидации", "Пожалуйста, введите корректный email (должен содержать @ и .)");
            return;
        }

        // Валидация пароля
        String password = txtPassword.getText();
        if (password.isEmpty()) {
            showAlert("Ошибка валидации", "Пожалуйста, введите пароль.");
            return;
        }
        if (password.length() < 6) {
            showAlert("Ошибка валидации", "Пароль должен содержать минимум 6 символов.");
            return;
        }

        // Проверка подтверждения пароля
        String confirmPassword = txtConfirmPassword.getText();
        if (!password.equals(confirmPassword)) {
            showAlert("Ошибка валидации", "Пароли не совпадают.");
            return;
        }

        // Проверка согласия с условиями
        if (!chkAgree.isSelected()) {
            showAlert("Ошибка валидации", "Вы должны согласиться с условиями использования.");
            return;
        }

        // Успешная регистрация
        lblStatus.setText("✅ Регистрация успешно завершена!");
        lblStatus.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Показ информационного сообщения
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Успешная регистрация");
        successAlert.setHeaderText("Поздравляем, " + firstName + " " + lastName + "!");
        successAlert.setContentText("Вы успешно зарегистрировались.\nEmail: " + email);
        successAlert.showAndWait();
    }

    @FXML
    protected void onClearClick() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        chkAgree.setSelected(false);
        lblStatus.setText("Введите данные для регистрации");
        lblStatus.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}