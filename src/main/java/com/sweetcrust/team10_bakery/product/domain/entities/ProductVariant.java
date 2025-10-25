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

    private String flavour;

    private BigDecimal priceModifier;

    protected ProductVariant() {
    }

    public ProductVariant(ProductSize size, BigDecimal priceModifier) {
        this.variantId = new VariantId();
        setPriceModifier(priceModifier);
        setSize(size);
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public ProductSize getSize() {
        return size;
    }

    public String getFlavour() {
        return flavour;
    }

    public BigDecimal getPriceModifier() {
        return priceModifier;
    }

    public void setFlavour(String flavour) {

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

}
