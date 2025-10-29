package com.sweetcrust.team10_bakery.cart.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @EmbeddedId
    private CartId cartId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private UserId ownerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id", nullable = false)
    private final List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
        this.cartId = new CartId();
    }

    public CartId getCartId() {
        return cartId;
    }

    public UserId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UserId ownerId) {
        if (ownerId == null) {
            throw new CartDomainException("ownerId", "ownerId must not be null");
        }
        this.ownerId = ownerId;
    }

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems);
    }

    public void addCartItem(CartItem newItem) {
        if (newItem == null) {
            throw new CartDomainException("cartItem", "cartItem must not be null");
        }

        // Combine quantities if the same variant exists
        cartItems.stream()
                .filter(item -> item.isSameVariant(newItem.getVariantId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> existingItem.increaseQuantity(newItem.getQuantity()),
                        () -> cartItems.add(newItem)
                );
    }

    public void removeCartItem(CartItemId cartItemId) {
        if (cartItemId == null) {
            throw new CartDomainException("cartItemId", "cartItemId must not be null");
        }

        boolean removed = cartItems.removeIf(item -> item.getCartItemId().equals(cartItemId));

        if (!removed) {
            throw new CartDomainException("cartItemId", "CartItem not found");
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
