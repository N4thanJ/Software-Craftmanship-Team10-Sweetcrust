package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @EmbeddedId
    private VariantId variantId;

    private String variantName;

    private BigDecimal priceModifier;

    protected ProductVariant() {
    }

    public ProductVariant(String variantName, BigDecimal priceModifier) {
        this.variantId = new VariantId();
        setVariantName(variantName);
        setPriceModifier(priceModifier);
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public BigDecimal getPriceModifier() {
        return priceModifier;
    }

    public void setVariantName(String variantName) {
        if (variantName == null || variantName.isBlank()) {
            throw new ProductDomainException("variant", "variantName should not be null or blank");
        }
        this.variantName = variantName.trim();
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

}
