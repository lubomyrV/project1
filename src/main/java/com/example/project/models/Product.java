package com.example.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;
    private double price;
    private String description;
    private String image;
    private String realPath;

    public Product() {
    }

    public Product(String model) {
        this.model = model;
    }

    public Product(String model, Double price) {
        this.model = model;
        this.price = price;
    }

    public Product(String model, Double price, String description) {
        this.model = model;
        this.price = price;
        this.description = description;
    }

    public Product(String model, Double price, String description, String image) {
        this.model = model;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", realPath='" + realPath + '\'' +
                '}';
    }
}
