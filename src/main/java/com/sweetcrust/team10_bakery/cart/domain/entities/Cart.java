package com.sweetcrust.team10_bakery.cart.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
}
