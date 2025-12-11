package com.sweetcrust.team10_bakery.cart.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import org.junit.jupiter.api.Test;

public class CartTest {

  @Test
  void givenDefaultConstructor_whenCreatingCart_thenCartIdIsGenerated() {
    // when
    Cart cart = new Cart();

    // then
    assertNotNull(cart.getCartId());
    assertInstanceOf(CartId.class, cart.getCartId());
  }
}
