package com.hmaresc.TFG_Servidor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonProperty("user_id")
    @JsonIgnore
    private User user;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    private String quantity;
    private Float productQuantity;

    @Column(name = "product_quantity_unit")
    private String productQuantityUnit;

    @Column(name = "serving_size")
    private String servingSize;

    @Column(name = "calories_per_serving")
    private Float caloriesPerServing;

    @Column(name = "calories_per_100g")
    private Float caloriesPer100g;

    @Column(name = "proteins_per_100g")
    private Float proteinsPer100g;

    @Column(name = "fat_per_100g")
    private Float fatPer100g;

    @Column(name = "carbs_per_100g")
    private Float carbsPer100g;

    @Column(name = "sugars_per_100g")
    private Float sugarsPer100g;

    @Column(name = "fiber_per_100g")
    private Float fiberPer100g;

    private String image;
    private String nutriscore;

    @ElementCollection
    private List<String> allergens;

    private String ingredients;

    @ElementCollection
    private List<String> countries;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Float getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Float productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductQuantityUnit() {
        return productQuantityUnit;
    }

    public void setProductQuantityUnit(String productQuantityUnit) {
        this.productQuantityUnit = productQuantityUnit;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public Float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(Float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public Float getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(Float caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public Float getProteinsPer100g() {
        return proteinsPer100g;
    }

    public void setProteinsPer100g(Float proteinsPer100g) {
        this.proteinsPer100g = proteinsPer100g;
    }

    public Float getFatPer100g() {
        return fatPer100g;
    }

    public void setFatPer100g(Float fatPer100g) {
        this.fatPer100g = fatPer100g;
    }

    public Float getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(Float carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public Float getSugarsPer100g() {
        return sugarsPer100g;
    }

    public void setSugarsPer100g(Float sugarsPer100g) {
        this.sugarsPer100g = sugarsPer100g;
    }

    public Float getFiberPer100g() {
        return fiberPer100g;
    }

    public void setFiberPer100g(Float fiberPer100g) {
        this.fiberPer100g = fiberPer100g;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNutriscore() {
        return nutriscore;
    }

    public void setNutriscore(String nutriscore) {
        this.nutriscore = nutriscore;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}
