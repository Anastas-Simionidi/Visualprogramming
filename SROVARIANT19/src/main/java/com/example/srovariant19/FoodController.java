package com.example.srovariant19;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class FoodController {

    @FXML private TextField          searchField;
    @FXML private ComboBox<String>   categoryFilter;
    @FXML private TableView<FoodItem> table;
    @FXML private TableColumn<FoodItem, String> nameCol, categoryCol;
    @FXML private TableColumn<FoodItem, Number> caloriesCol, proteinsCol, fatsCol,
            carbsCol, weightCol, totalCaloriesCol;
    @FXML private Label statusLabel;
    @FXML private Label totalsLabel;

    private final ObservableList<FoodItem> allItems = FXCollections.observableArrayList();
    private FilteredList<FoodItem> filteredItems;

    private static final String[] CATEGORIES = {
            "Фрукты", "Овощи", "Мясо", "Рыба", "Молочные", "Крупы", "Напитки", "Сладости"
    };

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());
        categoryCol.setCellValueFactory(c -> c.getValue().categoryProperty());
        caloriesCol.setCellValueFactory(c -> c.getValue().caloriesProperty());
        proteinsCol.setCellValueFactory(c -> c.getValue().proteinsProperty());
        fatsCol.setCellValueFactory(c -> c.getValue().fatsProperty());
        carbsCol.setCellValueFactory(c -> c.getValue().carbsProperty());
        weightCol.setCellValueFactory(c -> c.getValue().weightProperty());
        totalCaloriesCol.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(Math.round(c.getValue().getTotalCalories() * 10) / 10.0));

        filteredItems = new FilteredList<>(allItems, p -> true);
        table.setItems(filteredItems);

        categoryFilter.getItems().add("Все");
        categoryFilter.getItems().addAll(CATEGORIES);
        categoryFilter.getSelectionModel().selectFirst();
        categoryFilter.valueProperty().addListener((o, a, b) -> applyFilter());
        searchField.textProperty().addListener((o, a, b) -> applyFilter());

        table.setRowFactory(tv -> {
            TableRow<FoodItem> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) onEdit();
            });
            return row;
        });

        Platform.runLater(() -> {
            Scene scene = table.getScene();
            if (scene != null) {
                scene.getAccelerators().put(
                        new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN),
                        () -> searchField.requestFocus());
            }
        });

        loadDemoData();
        applyFilter();
        updateStatus();
    }

    @FXML
    private void onAdd() {
        FoodItem created = showFormDialog(null);
        if (created != null) {
            allItems.add(created);
            table.getSelectionModel().select(created);
            applyFilter();
            updateStatus();
        }
    }

    @FXML
    private void onEdit() {
        FoodItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Не выбран продукт", "Выберите запись в таблице для редактирования.");
            return;
        }
        FoodItem result = showFormDialog(selected);
        if (result != null) {
            table.refresh();
            applyFilter();
            updateStatus();
        }
    }

    @FXML
    private void onDelete() {
        FoodItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Не выбран продукт", "Выберите запись в таблице для удаления.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Удалить \"" + selected.getName() + "\"?",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Подтверждение удаления");
        confirm.initOwner(getOwner());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            allItems.remove(selected);
            updateStatus();
        }
    }

    @FXML
    private void onResetFilters() {
        searchField.clear();
        categoryFilter.getSelectionModel().selectFirst();
        applyFilter();
    }

    @FXML
    private void onLoadDemo() {
        allItems.clear();
        loadDemoData();
        applyFilter();
        updateStatus();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    @FXML
    private void onAbout() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("О программе");
        a.setHeaderText("Калькулятор питания v1.0");
        a.setContentText("JavaFX мини-проект (СРО №1, вариант 19).\n"
                + "Учёт продуктов, расчёт КБЖУ и итоговых значений.");
        a.initOwner(getOwner());
        a.showAndWait();
    }

    private FoodItem showFormDialog(FoodItem item) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/srovariant19/food-form.fxml"));
            Scene scene = new Scene(loader.load());
            Stage dialog = new Stage();
            dialog.setTitle(item == null ? "Новый продукт" : "Редактирование продукта");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(getOwner());
            dialog.setResizable(false);
            dialog.setScene(scene);

            FoodFormController c = loader.getController();
            c.setDialogStage(dialog);
            c.setItem(item);

            dialog.showAndWait();
            return c.isSaved() ? c.getResult() : null;
        } catch (IOException e) {
            showError("Ошибка", "Не удалось открыть форму: " + e.getMessage());
            return null;
        }
    }

    private void applyFilter() {
        String q = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
        String cat = categoryFilter.getValue();

        filteredItems.setPredicate(item -> {
            boolean byName = q.isEmpty() || item.getName().toLowerCase().contains(q);
            boolean byCat  = cat == null || "Все".equals(cat) || cat.equals(item.getCategory());
            return byName && byCat;
        });
        updateStatus();
    }

    private void updateStatus() {
        int shown = filteredItems == null ? 0 : filteredItems.size();
        double cal = 0, p = 0, f = 0, c = 0;
        if (filteredItems != null) {
            for (FoodItem it : filteredItems) {
                cal += it.getTotalCalories();
                p   += it.getTotalProteins();
                f   += it.getTotalFats();
                c   += it.getTotalCarbs();
            }
        }
        statusLabel.setText("Показано: " + shown + " из " + allItems.size() + " записей");
        totalsLabel.setText(String.format(
                "Итого: %.1f ккал   |   Б: %.1f г   |   Ж: %.1f г   |   У: %.1f г",
                cal, p, f, c));
    }

    private void loadDemoData() {
        allItems.addAll(
                new FoodItem("Яблоко",            "Фрукты",    52,  0.3, 0.2, 14.0, 150),
                new FoodItem("Банан",             "Фрукты",    89,  1.1, 0.3, 23.0, 120),
                new FoodItem("Куриная грудка",    "Мясо",     165, 31.0, 3.6,  0.0, 200),
                new FoodItem("Лосось",            "Рыба",     208, 20.0,13.0,  0.0, 150),
                new FoodItem("Молоко 2.5%",       "Молочные",  52,  2.8, 2.5,  4.7, 250),
                new FoodItem("Рис отварной",      "Крупы",    130,  2.7, 0.3, 28.0, 180),
                new FoodItem("Гречка",            "Крупы",    343, 12.6, 3.3, 62.0, 100),
                new FoodItem("Помидор",           "Овощи",     18,  0.9, 0.2,  3.9, 100),
                new FoodItem("Огурец",            "Овощи",     15,  0.7, 0.1,  3.6, 100),
                new FoodItem("Шоколад",           "Сладости", 546,  4.9,31.0, 61.0,  30),
                new FoodItem("Кофе с молоком",    "Напитки",   35,  1.8, 1.5,  3.5, 200),
                new FoodItem("Сыр твёрдый",       "Молочные", 402, 25.0,33.0,  0.0,  50)
        );
    }

    private Window getOwner() {
        return table.getScene() == null ? null : table.getScene().getWindow();
    }

    private void showInfo(String header, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        a.setHeaderText(header);
        a.initOwner(getOwner());
        a.showAndWait();
    }

    private void showError(String header, String text) {
        Alert a = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        a.setHeaderText(header);
        a.initOwner(getOwner());
        a.showAndWait();
    }
}