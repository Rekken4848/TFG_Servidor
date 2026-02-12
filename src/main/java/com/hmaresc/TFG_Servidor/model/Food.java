package com.hmaresc.TFG_Servidor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    private String quantity;

    @Column(name = "product_quantity")
    @JsonProperty("product_quantity")
    private float productQuantity;

    @Column(name = "product_quantity_unit")
    @JsonProperty("product_quantity_unit")
    private String productQuantityUnit;

    @Column(name = "serving_size")
    @JsonProperty("serving_size")
    private String servingSize;

    @Column(name = "calories_per_serving")
    @JsonProperty("calories_per_serving")
    private float caloriesPerServing;

    @Column(name = "calories_per_100g")
    @JsonProperty("calories_per_100g")
    private float caloriesPer100g;

    @Column(name = "proteins_per_100g")
    @JsonProperty("proteins_per_100g")
    private float proteinsPer100g;

    @Column(name = "fat_per_100g")
    @JsonProperty("fat_per_100g")
    private float fatPer100g;

    @Column(name = "carbs_per_100g")
    @JsonProperty("carbs_per_100g")
    private float carbsPer100g;

    @Column(name = "sugars_per_100g")
    @JsonProperty("sugars_per_100g")
    private float sugarsPer100g;

    @Column(name = "fiber_per_100g")
    @JsonProperty("fiber_per_100g")
    private float fiberPer100g;

    private String image;

    private String nutriscore;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "allergens", columnDefinition = "text[]")
    private String[] allergens;

    private String ingredients;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "countries", columnDefinition = "text[]")
    private String[] countries;

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

    public float getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(float productQuantity) {
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

    public float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public float getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(float caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public float getProteinsPer100g() {
        return proteinsPer100g;
    }

    public void setProteinsPer100g(float proteinsPer100g) {
        this.proteinsPer100g = proteinsPer100g;
    }

    public float getFatPer100g() {
        return fatPer100g;
    }

    public void setFatPer100g(float fatPer100g) {
        this.fatPer100g = fatPer100g;
    }

    public float getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(float carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public float getSugarsPer100g() {
        return sugarsPer100g;
    }

    public void setSugarsPer100g(float sugarsPer100g) {
        this.sugarsPer100g = sugarsPer100g;
    }

    public float getFiberPer100g() {
        return fiberPer100g;
    }

    public void setFiberPer100g(float fiberPer100g) {
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

    public String[] getAllergens() {
        return allergens;
    }

    public void setAllergens(String[] allergens) {
        this.allergens = allergens;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }
}
