package kz.atu.lab10;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class NotesController {
    @FXML private ListView<Note> listNotes;
    @FXML private TextField txtTitle;
    @FXML private TextArea txtBody;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbFilterCategory;
    @FXML private Label lblStatus;

    private final ObservableList<Note> notesList = FXCollections.observableArrayList();
    private FilteredList<Note> filteredList;
    private final NoteStorage storage = new NoteStorage();

    private final ObservableList<String> categories = FXCollections.observableArrayList(
            "Все категории", "Математика", "Программирование", "История", "Физика", "Философия"
    );

    @FXML
    public void initialize() {
        // Загрузка 10 демонстрационных записей для Варианта №1
        loadDemoData();

        // Настройка списков и фильтрации
        filteredList = new FilteredList<>(notesList, p -> true);
        listNotes.setItems(filteredList);

        cmbCategory.setItems(FXCollections.observableArrayList("Математика", "Программирование", "История", "Физика", "Философия"));
        cmbFilterCategory.setItems(categories);
        cmbFilterCategory.setValue("Все категории");

        // Слушатель выбора записи в списке
        listNotes.getSelectionModel().selectedItemProperty().addListener((obs, oldNote, newNote) -> {
            if (newNote != null) {
                txtTitle.setText(newNote.getTitle());
                txtBody.setText(newNote.getBody());
                cmbCategory.setValue(newNote.getCategory());
            }
        });

        // Слушатели для динамического поиска и фильтрации
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> updateFilter());
        cmbFilterCategory.valueProperty().addListener((obs, oldVal, newVal) -> updateFilter());

        lblStatus.setText("Загружено 10 демо-записей конспектов.");
    }

    private void updateFilter() {
        String searchText = txtSearch.getText().toLowerCase().trim();
        String selectedCategory = cmbFilterCategory.getValue();

        filteredList.setPredicate(note -> {
            boolean matchesSearch = note.getTitle().toLowerCase().contains(searchText);
            boolean matchesCategory = selectedCategory == null ||
                    selectedCategory.equals("Все категории") ||
                    note.getCategory().equals(selectedCategory);
            return matchesSearch && matchesCategory;
        });
    }

    @FXML
    private void onAdd() {
        String title = txtTitle.getText().trim();
        String body = txtBody.getText().trim();
        String category = cmbCategory.getValue() != null ? cmbCategory.getValue().trim() : "";

        if (title.isBlank() || body.isBlank() || category.isBlank()) {
            showError("Заполните заголовок, текст конспекта и выберите/введите категорию.");
            return;
        }

        Note note = new Note(title, body, category);
        notesList.add(note);
        listNotes.getSelectionModel().select(note);
        lblStatus.setText("Конспект добавлен: " + title);
    }

    @FXML
    private void onEdit() {
        Note selected = listNotes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите конспект из списка для изменения.");
            return;
        }

        String title = txtTitle.getText().trim();
        String body = txtBody.getText().trim();
        String category = cmbCategory.getValue() != null ? cmbCategory.getValue().trim() : "";

        if (title.isBlank() || body.isBlank() || category.isBlank()) {
            showError("Поля не могут быть пустыми.");
            return;
        }

        selected.setTitle(title);
        selected.setBody(body);
        selected.setCategory(category);

        listNotes.refresh(); // Обновляем отображение текста в ListView
        lblStatus.setText("Конспект обновлен: " + title);
    }

    @FXML
    private void onDelete() {
        Note selected = listNotes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите запись для удаления.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Удалить выбранный конспект?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            notesList.remove(selected);
            txtTitle.clear();
            txtBody.clear();
            cmbCategory.setValue(null);
            lblStatus.setText("Конспект удален.");
        }
    }

    @FXML
    private void onSave() {
        FileChooser chooser = createChooser();
        File file = chooser.showSaveDialog(listNotes.getScene().getWindow());
        if (file == null) return;

        try {
            storage.save(file.toPath(), notesList);
            lblStatus.setText("Успешно сохранено в JSON: " + file.getName());
        } catch (IOException ex) {
            showError("Не удалось сохранить файл.");
        }
    }

    @FXML
    private void onOpen() {
        FileChooser chooser = createChooser();
        File file = chooser.showOpenDialog(listNotes.getScene().getWindow());
        if (file == null) return;

        try {
            var loaded = storage.load(file.toPath());
            notesList.setAll(loaded);
            lblStatus.setText("Загружено конспектов из файла: " + notesList.size());
        } catch (IOException ex) {
            showError("Не удалось прочитать поврежденный или некорректный JSON.");
        }
    }

    private FileChooser createChooser() {
        FileChooser c = new FileChooser();
        c.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON (*.json)", "*.json"));
        return c;
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    private void loadDemoData() {
        notesList.add(new Note("Матрицы и определители", "Лекция по высшей математике. Свойства определителей порядка N...", "Математика"));
        notesList.add(new Note("Пределы функций", "Замечательные пределы. Теорема о зажатой переменной...", "Математика"));
        notesList.add(new Note("Введение в JavaFX", "Жизненный цикл Application. Разметка FXML и контроллеры...", "Программирование"));
        notesList.add(new Note("Сериализация в JSON", "Использование объектного маппера библиотеки Jackson Databind...", "Программирование"));
        notesList.add(new Note("Древний Казахстан", "Эпоха бронзы, сакские племена и памятники андроновской культуры...", "История"));
        notesList.add(new Note("Законы Ньютона", "Классическая механика. Три фундаментальных закона движения тел...", "Физика"));
        notesList.add(new Note("Оптика и кванты", "Фотоэффект, корпускулярно-волновой дуализм света...", "Физика"));
        notesList.add(new Note("Философия античности", "Труды Платона и Аристотеля. Концепция мира идей...", "Философия"));
        notesList.add(new Note("Объектно-ориентированный подход", "Основные принципы: инкапсуляция, наследование, полиморфизм...", "Программирование"));
        notesList.add(new Note("Интегральное исчисление", "Вычисление неопределенных интегралов методом замены переменной...", "Математика"));
    }
}
