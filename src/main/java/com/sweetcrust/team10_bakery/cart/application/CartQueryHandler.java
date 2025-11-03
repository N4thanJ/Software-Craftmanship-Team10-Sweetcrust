package com.sweetcrust.team10_bakery.cart.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;

@Service
public class CartQueryHandler {
    private CartRepository cartRepository;

    public CartQueryHandler(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
