package com.sweetcrust.team10_bakery.cart.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;

@Service
public class CartQueryService {
    private CartRepository cartRepository;

    public CartQueryService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
