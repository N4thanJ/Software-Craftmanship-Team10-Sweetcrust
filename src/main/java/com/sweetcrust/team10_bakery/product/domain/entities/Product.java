package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @EmbeddedId
    private ProductId productId;

    private String name;

    private String description;

    private BigDecimal basePrice;

    private boolean available;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "category_id"))
    private CategoryId categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductVariant> variants = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, String description, BigDecimal basePrice, boolean available, CategoryId categoryId) {
        this.productId = new ProductId();
        setName(name);
        setDescription(description);
        setBasePrice(basePrice);
        setAvailable(available);
        setCategoryId(categoryId);
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<ProductVariant> getVariants() {
        return List.copyOf(variants);
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductDomainException("product", "name should not be blank or null");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new ProductDomainException("product", "description should not be blank or null");
        }
        this.description = description;
    }

    public void setBasePrice(BigDecimal basePrice) {
        if (basePrice == null) {
            throw new ProductDomainException("product", "basePrice should not be null");
        }
        if (basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductDomainException("product", "basePrice should be greater than 0");
        }
        this.basePrice = basePrice;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void addVariant(ProductVariant variant) {
        if (variant == null) {
            throw new ProductDomainException("variant", "variant cannot be null");
        }
        variants.add(variant);
    }

    public void setCategoryId(CategoryId categoryId) {
        if (categoryId == null) {
            throw new ProductDomainException("product", "categoryId should not be null");
        }
        this.categoryId = categoryId;
    }

}
