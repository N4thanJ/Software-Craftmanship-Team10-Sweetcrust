package com.sweetcrust.team10_bakery.cart.application;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartItemRepository;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.cart.presentation.CartMapper;
import com.sweetcrust.team10_bakery.cart.presentation.dto.CartResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartQueryHandler {
  private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartQueryHandler(CartRepository cartRepository, CartItemRepository cartItemRepository) {
    this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

  public List<CartResponse> getAllCarts() {
    List<Cart> carts = cartRepository.findAll();

    return carts.stream()
            .map(cart -> {
                List<CartItem> items = cartItemRepository.findByCartId(cart.getCartId());
                return CartMapper.toCartResponse(cart, items);
            })
            .toList();
  }
}
