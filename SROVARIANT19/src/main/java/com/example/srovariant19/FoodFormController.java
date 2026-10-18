package com.example.srovariant19;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FoodFormController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField caloriesField;
    @FXML private TextField proteinsField;
    @FXML private TextField fatsField;
    @FXML private TextField carbsField;
    @FXML private Spinner<Double> weightSpinner;

    private Stage dialogStage;
    private FoodItem editing;
    private FoodItem result;
    private boolean saved = false;

    private static final String[] CATEGORIES = {
            "Фрукты", "Овощи", "Мясо", "Рыба", "Молочные", "Крупы", "Напитки", "Сладости"
    };

    @FXML
    public void initialize() {
        categoryBox.getItems().addAll(CATEGORIES);
        categoryBox.getSelectionModel().selectFirst();

        SpinnerValueFactory<Double> vf =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 5000, 100, 10);
        weightSpinner.setValueFactory(vf);

        weightSpinner.getEditor().textProperty().addListener((o, a, b) -> {
            try {
                double v = Double.parseDouble(b.replace(',', '.').trim());
                if (v > 0) weightSpinner.getValueFactory().setValue(v);
            } catch (NumberFormatException ignored) { }
        });
    }

    public void setDialogStage(Stage stage) { this.dialogStage = stage; }

    public void setItem(FoodItem item) {
        this.editing = item;
        if (item != null) {
            nameField.setText(item.getName());
            categoryBox.setValue(item.getCategory());
            caloriesField.setText(String.valueOf(item.getCalories()));
            proteinsField.setText(String.valueOf(item.getProteins()));
            fatsField.setText(String.valueOf(item.getFats()));
            carbsField.setText(String.valueOf(item.getCarbs()));
            weightSpinner.getValueFactory().setValue(item.getWeight());
        }
    }

    public boolean isSaved()     { return saved; }
    public FoodItem getResult()  { return result; }

    @FXML
    private void onSave() {
        try {
            String name = nameField.getText() == null ? "" : nameField.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Введите название продукта.");
            }
            if (name.length() > 80) {
                throw new IllegalArgumentException("Название слишком длинное (макс. 80 символов).");
            }
            String cat = categoryBox.getValue();
            if (cat == null || cat.isBlank()) {
                throw new IllegalArgumentException("Выберите категорию.");
            }

            double cal  = parseNonNegative(caloriesField.getText(), "Калорийность");
            double prot = parseNonNegative(proteinsField.getText(), "Белки");
            double fat  = parseNonNegative(fatsField.getText(), "Жиры");
            double carb = parseNonNegative(carbsField.getText(), "Углеводы");

            double weight;
            try {
                weight = Double.parseDouble(
                        weightSpinner.getEditor().getText().replace(',', '.').trim());
            } catch (Exception ex) {
                weight = weightSpinner.getValue() == null ? 0 : weightSpinner.getValue();
            }
            if (weight <= 0) {
                throw new IllegalArgumentException("Вес порции должен быть больше нуля.");
            }
            if (weight > 5000) {
                throw new IllegalArgumentException("Вес порции не должен превышать 5000 г.");
            }

            if (editing == null) editing = new FoodItem();
            editing.setName(name);
            editing.setCategory(cat);
            editing.setCalories(cal);
            editing.setProteins(prot);
            editing.setFats(fat);
            editing.setCarbs(carb);
            editing.setWeight(weight);

            result = editing;
            saved = true;
            dialogStage.close();

        } catch (IllegalArgumentException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Ошибка ввода");
            a.setHeaderText("Некорректные данные");
            a.setContentText(ex.getMessage());
            a.initOwner(dialogStage);
            a.showAndWait();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Ошибка");
            a.setHeaderText("Непредвиденная ошибка");
            a.setContentText(String.valueOf(ex.getMessage()));
            a.initOwner(dialogStage);
            a.showAndWait();
        }
    }

    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    private double parseNonNegative(String raw, String label) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException(label + ": поле обязательно для заполнения.");
        }
        try {
            double v = Double.parseDouble(raw.replace(',', '.').trim());
            if (v < 0) throw new NumberFormatException();
            if (v > 10000) throw new IllegalArgumentException(label + ": значение слишком большое.");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + ": введите неотрицательное число.");
        }
    }
}