package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
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

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    protected ProductVariant() {
    }

    public ProductVariant(ProductSize size, String variantName, BigDecimal priceModifier, ProductId productId) {
        this.variantId = new VariantId();
        setSize(size);
        setVariantName(variantName);
        setPriceModifier(priceModifier);
        setProductId(productId);
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

    public ProductId getProductId() {
        return productId;
    }

    public Product getProduct() {
        return product;
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

    public void setProductId(ProductId productId) {
        if  (productId == null) {
            throw new ProductDomainException("productId", "productId should not be null");
        }
        this.productId = productId;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new ProductDomainException("product", "product should not be null");
        }
        this.product = product;
    }

    @Transient
    public BigDecimal getPrice() {
        if (product == null) {
            throw new ProductDomainException("product", "Product must be loaded to compute price");
        }

        BigDecimal basePrice = product.getBasePrice();
        BigDecimal modifier = priceModifier != null ? priceModifier : BigDecimal.ZERO;

        return basePrice.add(modifier);
    }

}