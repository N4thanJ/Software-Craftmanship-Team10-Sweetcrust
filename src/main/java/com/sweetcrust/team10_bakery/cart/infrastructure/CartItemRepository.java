package com.sweetcrust.team10_bakery.cart.infrastructure;

import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    Optional<CartItem> getCartItemByCartItemId(CartItemId cartItemId);

    List<CartItem> findByCartId(CartId cartId);
}
