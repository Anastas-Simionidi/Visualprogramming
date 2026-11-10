package com.example.demo.service;

import javafx.collections.*;
import com.example.demo.model.Product;

public class ProductService {
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(String name, String category, int quantity) {
        validate(name, category, quantity);
        checkForDuplicate(name, category, null);
        products.add(new Product(name.trim(), category.trim(), quantity));
    }

    public void updateProduct(Product p, String name, String category, int quantity) {
        if (p == null) throw new IllegalArgumentException("Товар не выбран");
        validate(name, category, quantity);
        checkForDuplicate(name, category, p);

        p.setName(name.trim());
        p.setCategory(category.trim());
        p.setQuantity(quantity);
    }

    public void deleteProduct(Product p) {
        if (p == null) throw new IllegalArgumentException("Товар не выбран");
        products.remove(p);
    }

    public int getTotalQuantity() {
        return products.stream().mapToInt(Product::getQuantity).sum();
    }

    private void validate(String name, String category, int quantity) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Введите наименование товара");
        if (category == null || category.isBlank()) throw new IllegalArgumentException("Введите категорию");
        if (quantity < 0 || quantity > 10000) {
            throw new IllegalArgumentException("Количество должно быть от 0 до 10,000");
        }
    }

    private void checkForDuplicate(String name, String category, Product currentProduct) {
        for (Product p : products) {
            if (p != currentProduct &&
                    p.getName().equalsIgnoreCase(name.trim()) &&
                    p.getCategory().equalsIgnoreCase(category.trim())) {
                throw new IllegalArgumentException("Такой товар в этой категории уже существует!");
            }
        }
    }
}
