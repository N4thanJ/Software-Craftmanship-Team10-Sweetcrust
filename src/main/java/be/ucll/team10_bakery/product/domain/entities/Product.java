package be.ucll.team10_bakery.product.domain.entities;

import java.util.UUID;

import be.ucll.team10_bakery.product.domain.valueobjects.ProductId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product {

    @EmbeddedId
    private ProductId id;

    @NotBlank(message = "Name of product should not be empty")
    private String name;

    public Product() {
        setName(name);
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
