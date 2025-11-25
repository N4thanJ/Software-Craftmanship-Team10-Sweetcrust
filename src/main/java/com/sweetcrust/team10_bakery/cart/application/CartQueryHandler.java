package com.sweetcrust.team10_bakery.cart.application;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartQueryHandler {
  private final CartRepository cartRepository;

  public CartQueryHandler(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public List<Cart> getAllCarts() {
    return cartRepository.findAll();
  }
}
