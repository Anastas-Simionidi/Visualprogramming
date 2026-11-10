package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import java.util.Optional;
import java.util.stream.Collectors;

public class HelloController {
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, String> colCategory;
    @FXML private TableColumn<Product, Number> colQuantity;

    @FXML private TextField txtName;
    @FXML private TextField txtCategory;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cmbCategoryFilter;
    @FXML private Label lblCount;

    private final ProductService service = new ProductService();
    private FilteredList<Product> filtered;

    @FXML
    private void initialize() {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colCategory.setCellValueFactory(c -> c.getValue().categoryProperty());
        colQuantity.setCellValueFactory(c -> c.getValue().quantityProperty());

        service.addProduct("Процессор Intel i5", "Электроника", 15);
        service.addProduct("Монитор 24'", "Электроника", 8);
        service.addProduct("Офисный стол", "Мебель", 4);

        filtered = new FilteredList<>(service.getProducts(), p -> true);
        SortedList<Product> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tableProducts.comparatorProperty());
        tableProducts.setItems(sorted);

        txtSearch.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        cmbCategoryFilter.valueProperty().addListener((o, oldVal, newVal) -> applyFilter());

        tableProducts.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                txtName.setText(newVal.getName());
                txtCategory.setText(newVal.getCategory());
                txtQuantity.setText(String.valueOf(newVal.getQuantity()));
            }
        });

        updateCategoriesFilter();
        updateUIProperties();
    }

    private void applyFilter() {
        String searchTxt = txtSearch.getText().trim().toLowerCase();
        String selectedCategory = cmbCategoryFilter.getValue();

        filtered.setPredicate(p -> {
            boolean matchesSearch = p.getName().toLowerCase().contains(searchTxt);
            boolean matchesCategory = selectedCategory == null ||
                    selectedCategory.equals("Все") ||
                    p.getCategory().equals(selectedCategory);
            return matchesSearch && matchesCategory;
        });
        updateUIProperties();
    }

    private void updateCategoriesFilter() {
        String currentSelection = cmbCategoryFilter.getValue();
        cmbCategoryFilter.getItems().clear();
        cmbCategoryFilter.getItems().add("Все");

        var categories = service.getProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());

        cmbCategoryFilter.getItems().addAll(categories);

        if (cmbCategoryFilter.getItems().contains(currentSelection)) {
            cmbCategoryFilter.setValue(currentSelection);
        } else {
            cmbCategoryFilter.setValue("Все");
        }
    }

    private void updateUIProperties() {
        lblCount.setText("Найдено позиций: " + filtered.size() + " | Всего единиц: " + service.getTotalQuantity());
    }

    private int readQuantity() {
        try {
            return Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите целое число в поле 'Количество'");
        }
    }

    @FXML
    private void onAdd() {
        try {
            service.addProduct(txtName.getText(), txtCategory.getText(), readQuantity());
            updateCategoriesFilter();
            applyFilter();
            clearFields();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onUpdate() {
        try {
            Product selected = tableProducts.getSelectionModel().getSelectedItem();
            service.updateProduct(selected, txtName.getText(), txtCategory.getText(), readQuantity());
            updateCategoriesFilter();
            applyFilter();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        try {
            Product selected = tableProducts.getSelectionModel().getSelectedItem();
            if (selected == null) throw new IllegalArgumentException("Товар не выбран");

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение");
            confirm.setHeaderText(null);
            confirm.setContentText("Удалить товар: " + selected.getName() + "?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                service.deleteProduct(selected);
                updateCategoriesFilter();
                applyFilter();
                clearFields();
            }
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void clearFields() {
        txtName.clear();
        txtCategory.clear();
        txtQuantity.clear();
        tableProducts.getSelectionModel().clearSelection();
    }

    private void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Ошибка");
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
