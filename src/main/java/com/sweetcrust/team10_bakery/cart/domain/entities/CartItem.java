package com.sweetcrust.team10_bakery.cart.domain.entities;

import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "variant_id"))
    private VariantId variantId;

    private int quantity;

    private BigDecimal unitPrice;

    protected CartItem() {
    }

    public CartItem(ProductId productId, VariantId variantId, int quantity, BigDecimal unitPrice) {
        this.cartItemId = new CartItemId();
        setProductId(productId);
        setVariantId(variantId);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public CartItemId getCartItemId() {
        return cartItemId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void setProductId(ProductId productId) {
        if (productId == null) {
            throw new CartDomainException("productId", "productId must not be null");
        }
        this.productId = productId;
    }

    public void setVariantId(VariantId variantId) {
        if (variantId == null) {
            throw new CartDomainException("variantId", "variantId must not be null");
        }
        this.variantId = variantId;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new CartDomainException("quantity", "quantity must be positive");
        }
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            throw new CartDomainException("unitPrice", "unitPrice must not be null");
        }
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new CartDomainException("unitPrice", "unitPrice must not be negative");
        }
        this.unitPrice = unitPrice;
    }
}