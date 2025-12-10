package com.sweetcrust.team10_bakery.cart.presentation;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.presentation.dto.CartItemResponse;
import com.sweetcrust.team10_bakery.cart.presentation.dto.CartResponse;
import java.util.List;

public class CartMapper {

  public static CartResponse toCartResponse(Cart cart, List<CartItem> cartItems) {
    return new CartResponse(
        cart.getCartId(),
        cart.getOwnerId(),
        cartItems.stream().map(CartMapper::toCartItemResponse).toList());
  }

  public static CartItemResponse toCartItemResponse(CartItem cartItem) {
    return new CartItemResponse(
        cartItem.getCartItemId(),
        cartItem.getVariantId(),
        cartItem.getQuantity(),
        cartItem.getUnitPrice(),
        cartItem.getTotalPrice());
  }
}
