package be.ucll.team10_bakery.product.domain.entities;


import be.ucll.team10_bakery.product.domain.valueobjects.ProductId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product {

    @EmbeddedId
    private ProductId productId = new ProductId();

    @NotBlank(message = "Name of product should not be empty")
    private String name;

    protected Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
