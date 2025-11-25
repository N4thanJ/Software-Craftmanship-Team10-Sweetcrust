package com.sweetcrust.team10_bakery.cart.domain.entities;

import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {

  @EmbeddedId private CartItemId cartItemId;

  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "variant_id"))
  private VariantId variantId;

  private int quantity;

  private final BigDecimal unitPrice;

  protected CartItem() {
    this.unitPrice = BigDecimal.ZERO;
  }

  private CartItem(VariantId variantId, int quantity, BigDecimal unitPrice) {
    if (variantId == null) {
      throw new CartDomainException("variantId", "variantId must not be null");
    }
    if (quantity <= 0) {
      throw new CartDomainException("quantity", "quantity must be positive");
    }
    if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new CartDomainException("unitPrice", "unitPrice must not be null or negative");
    }

    this.cartItemId = new CartItemId();
    this.variantId = variantId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  // FACTORY METHOD PATTERN
  public static CartItem fromVariant(ProductVariant variant, int quantity) {
    if (variant == null) {
      throw new CartDomainException("variant", "variant must not be null");
    }
    if (quantity <= 0) {
      throw new CartDomainException("quantity", "quantity must be positive");
    }

    return new CartItem(variant.getVariantId(), quantity, variant.getPrice());
  }

  public CartItemId getCartItemId() {
    return cartItemId;
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

  public void increaseQuantity(int amount) {
    if (amount <= 0) {
      throw new CartDomainException("quantity", "amount must be positive");
    }
    this.quantity += amount;
  }

  public void decreaseQuantity(int amount) {
    if (amount <= 0) {
      throw new CartDomainException("quantity", "amount must be positive");
    }
    if (quantity < amount) {
      throw new CartDomainException("quantity", "quantity must be greater than or equal to amount");
    }
    this.quantity -= amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CartItem other)) return false;
    return variantId.equals(other.variantId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variantId);
  }

  public boolean isSameVariant(VariantId variantId) {
    return this.variantId.equals(variantId);
  }
}
