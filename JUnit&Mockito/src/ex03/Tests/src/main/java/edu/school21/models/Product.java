package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long identifier;
    private String name;
    private Double price;

    public Product(Long identifier, String name, Double price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long id) {
        this.identifier = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double newPrice) {
        this.price = newPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return identifier.equals(product.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return new String("Product: { id=" + identifier + ", name=" + name + ", price=" + price + "}");
    }
}