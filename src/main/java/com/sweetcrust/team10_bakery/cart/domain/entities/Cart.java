package com.sweetcrust.team10_bakery.cart.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
    @EmbeddedId
    private CartId cartId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id", nullable = false)
    private final List<CartItem> cartItems = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Cart() {
    }

    public Cart(LocalDateTime createdAt) {
        this.cartId = new CartId();
        setCreatedAt(createdAt);
    }

    public CartId getCartId() {
        return cartId;
    }

    public void setCartId(CartId cartId) {
        this.cartId = cartId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems);
    }

    public void addCartItem(CartItem cartItem) {
        if (cartItem == null) {
            throw new CartDomainException("cartItem", "cartItem should not be null");
        }
        cartItems.add(cartItem);
    }

    public void removeCartItem(ProductId productId) {
        if (productId == null) {
            throw new CartDomainException("productId", "productId should not be null");
        }
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }

    public void updateCartItem(CartItem cartItem) {
        if (cartItem == null) {
            throw new CartDomainException("cartItem", "cartItem should not be null");
        }

        cartItems.stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(cartItem.getQuantity()),
                        () -> cartItems.add(cartItem));
    }
}
