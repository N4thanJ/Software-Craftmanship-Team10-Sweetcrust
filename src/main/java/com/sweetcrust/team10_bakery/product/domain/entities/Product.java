package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import jakarta.persistence.*;

import java.math.BigDecimal;

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
    @AttributeOverride(name = "id", column = @Column(name = "variant_id"))
    private VariantId variantId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "category_id"))
    private CategoryId categoryId;

    protected Product() {
    }

    public Product(String name, String description, BigDecimal basePrice, boolean available, VariantId variantId,
            CategoryId categoryId) {
        this.productId = new ProductId();
        setName(name);
        setDescription(description);
        setBasePrice(basePrice);
        setAvailable(available);
        setVariantId(variantId);
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

    public VariantId getVariantId() {
        return variantId;
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

    public void setVariantId(VariantId variantId) {
        if (variantId == null) {
            throw new ProductDomainException("variant", "variantId should not be null");
        }
        this.variantId = variantId;
    }

    public void setCategoryId(CategoryId categoryId) {
        if (categoryId == null) {
            throw new ProductDomainException("product", "categoryId should not be null");
        }
        this.categoryId = categoryId;
    }

}
