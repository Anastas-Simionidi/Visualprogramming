package com.example.srovariant19;

import javafx.beans.property.*;

public class FoodItem {

    private final StringProperty name     = new SimpleStringProperty(this, "name", "");
    private final StringProperty category = new SimpleStringProperty(this, "category", "");
    private final DoubleProperty calories = new SimpleDoubleProperty(this, "calories", 0);
    private final DoubleProperty proteins = new SimpleDoubleProperty(this, "proteins", 0);
    private final DoubleProperty fats     = new SimpleDoubleProperty(this, "fats", 0);
    private final DoubleProperty carbs    = new SimpleDoubleProperty(this, "carbs", 0);
    private final DoubleProperty weight   = new SimpleDoubleProperty(this, "weight", 0);

    public FoodItem() {}

    public FoodItem(String name, String category, double calories,
                    double proteins, double fats, double carbs, double weight) {
        this.name.set(name);
        this.category.set(category);
        this.calories.set(calories);
        this.proteins.set(proteins);
        this.fats.set(fats);
        this.carbs.set(carbs);
        this.weight.set(weight);
    }

    public StringProperty nameProperty()     { return name; }
    public StringProperty categoryProperty() { return category; }
    public DoubleProperty caloriesProperty() { return calories; }
    public DoubleProperty proteinsProperty() { return proteins; }
    public DoubleProperty fatsProperty()     { return fats; }
    public DoubleProperty carbsProperty()    { return carbs; }
    public DoubleProperty weightProperty()   { return weight; }

    public String getName()          { return name.get(); }
    public void   setName(String v)  { name.set(v); }

    public String getCategory()           { return category.get(); }
    public void   setCategory(String v)   { category.set(v); }

    public double getCalories()           { return calories.get(); }
    public void   setCalories(double v)   { calories.set(v); }

    public double getProteins()           { return proteins.get(); }
    public void   setProteins(double v)   { proteins.set(v); }

    public double getFats()               { return fats.get(); }
    public void   setFats(double v)       { fats.set(v); }

    public double getCarbs()              { return carbs.get(); }
    public void   setCarbs(double v)      { carbs.set(v); }

    public double getWeight()             { return weight.get(); }
    public void   setWeight(double v)     { weight.set(v); }

    public double getTotalCalories() { return getCalories() * getWeight() / 100.0; }
    public double getTotalProteins() { return getProteins() * getWeight() / 100.0; }
    public double getTotalFats()     { return getFats()     * getWeight() / 100.0; }
    public double getTotalCarbs()    { return getCarbs()    * getWeight() / 100.0; }
}