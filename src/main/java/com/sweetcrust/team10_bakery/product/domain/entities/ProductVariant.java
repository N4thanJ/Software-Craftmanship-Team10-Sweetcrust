package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @EmbeddedId
    private VariantId variantId;

    @Enumerated(EnumType.STRING)
    private ProductSize size;

    private String variantName;

    private BigDecimal priceModifier;

    protected ProductVariant() {
    }

    public ProductVariant(ProductSize size, String variantName, BigDecimal priceModifier) {
        this.variantId = new VariantId();
        setSize(size);
        setVariantName(variantName);
        setPriceModifier(priceModifier);
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public ProductSize getSize() {
        return size;
    }

    public String getVariantName() {
        return variantName;
    }

    public BigDecimal getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(BigDecimal priceModifier) {
        if (priceModifier == null) {
            throw new ProductDomainException("priceModifier", "priceModifier should not be null");
        }
        if (priceModifier.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductDomainException("priceModifier", "priceModifier should not be negative");
        }
        this.priceModifier = priceModifier;
    }

    public void setSize(ProductSize size) {
        if (size == null) {
            throw new ProductDomainException("size", "size should not be null");
        }
        this.size = size;
    }

    public void setVariantName(String variantName) {
        if (variantName == null || variantName.isBlank()) {
            throw new ProductDomainException("variantName", "variantName should not be null or blank");
        }
        this.variantName = variantName;
    }
}