package kz.atu.lab04;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LayoutController {
    @FXML private BorderPane rootPane;
    @FXML private VBox navigationBox;
    @FXML private TextField txtFullName;
    @FXML private TextField txtGroup;
    @FXML private ComboBox<String> cmbCourse;
    @FXML private ComboBox<String> cmbStudyForm;
    @FXML private TextArea txtNote;
    @FXML private TableView<Student> tblStudents;
    @FXML private Label lblStatus;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Инициализация выпадающих списков
        cmbCourse.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс");
        cmbCourse.getSelectionModel().selectFirst();

        cmbStudyForm.getItems().addAll("Очная", "Заочная", "Дистанционная");
        cmbStudyForm.getSelectionModel().selectFirst();

        tblStudents.setItems(studentList);
    }

    @FXML
    private void onSaveClick() {
        String name = txtFullName.getText().trim();
        String group = txtGroup.getText().trim();
        String course = cmbCourse.getValue();
        String studyForm = cmbStudyForm.getValue();
        String note = txtNote.getText().trim();

        if (name.isEmpty() || group.isEmpty()) {
            lblStatus.setText("Статус: заполните ФИО и группу");
            return;
        }

        Student student = new Student(name, group, course, studyForm, note);
        studentList.add(student);

        lblStatus.setText("Статус: данные сохранены для " + name);
        clearFields();
    }

    @FXML
    private void onClearClick() {
        clearFields();
        lblStatus.setText("Статус: форма очищена");
        txtFullName.requestFocus();
    }

    private void clearFields() {
        txtFullName.clear();
        txtGroup.clear();
        cmbCourse.getSelectionModel().selectFirst();
        cmbStudyForm.getSelectionModel().selectFirst();
        txtNote.clear();
    }

    // Самостоятельная часть №3: Скрытие/Отображение навигации
    @FXML
    private void onToggleNavClick() {
        if (rootPane.getLeft() != null) {
            rootPane.setLeft(null);
            lblStatus.setText("Статус: навигация скрыта");
        } else {
            rootPane.setLeft(navigationBox);
            lblStatus.setText("Статус: навигация отображена");
        }
    }

    // Самостоятельная часть №5: Открытие диалога «О программе»
    @FXML
    private void onAboutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("about-view.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("О программе");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootPane.getScene().getWindow());
            dialogStage.setScene(new Scene(page));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}