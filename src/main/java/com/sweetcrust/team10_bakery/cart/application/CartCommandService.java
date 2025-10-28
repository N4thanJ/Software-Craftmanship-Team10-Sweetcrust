package com.sweetcrust.team10_bakery.cart.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.cart.application.commands.AddCartItemCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.DeleteCartItemCommand;
import com.sweetcrust.team10_bakery.cart.domain.CartDomainException;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

@Service
public class CartCommandService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartCommandService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(CreateCartCommand createCartCommand) {
        if (createCartCommand.productId() == null) {
            throw new CartDomainException("productId", "Product id cannot be null");
        }

        if (createCartCommand.quantity() <= 0) {
            throw new CartDomainException("quantity", "Quantity must be greater than 0");
        }

        Product product = productRepository.findById(createCartCommand.productId())
                .orElseThrow(() -> new CartServiceException("product",
                        "Product with id " + createCartCommand.productId() + " could not be found"));

        Cart cart = new Cart(LocalDateTime.now());
        cart.addCartItem(new CartItem(product.getProductId(), product.getVariantId(), createCartCommand.quantity(),
                product.getBasePrice()));

        return cartRepository.save(cart);
    }

    public Cart addCardItem(CartId cartId, AddCartItemCommand addCardItemCommand) {

        if (cartId == null) {
            throw new CartDomainException("cartId", "cart id cannot be null");
        }

        if (addCardItemCommand.productId() == null) {
            throw new CartDomainException("productId", "Product id cannot be null");
        }

        if (addCardItemCommand.quantity() <= 0) {
            throw new CartDomainException("quantity", "Quantity must be greater than 0");
        }

        Product product = productRepository.findById(addCardItemCommand.productId())
                .orElseThrow(() -> new CartServiceException("product",
                        "Product with id " + addCardItemCommand.productId() + " could not be found"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartServiceException("cart", "Cart with id " + cartId + " could not be found"));

        if (cart.getCartItems().stream().anyMatch(item -> item.getProductId().equals(product.getProductId()))) {
            cart.updateCartItem(product.getProductId(), addCardItemCommand.quantity());
        } else {
            CartItem cartItem = new CartItem(product.getProductId(), product.getVariantId(),
                    addCardItemCommand.quantity(),
                    product.getBasePrice());
            cart.addCartItem(cartItem);
        }

        return cartRepository.save(cart);
    }

    public Cart removeCardItem(CartId cartId, DeleteCartItemCommand deleteCardItemCommand) {
        if (cartId == null) {
            throw new CartDomainException("cartId", "cart id cannot be null");
        }

        if (deleteCardItemCommand.productId() == null) {
            throw new CartDomainException("productId", "product id cannot be null");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartServiceException("cart", "Cart with id " + cartId + " could not be found"));

        boolean itemExists = cart.getCartItems().stream()
                .anyMatch(item -> item.getProductId().equals(deleteCardItemCommand.productId()));

        if (!itemExists) {
            throw new CartDomainException("productId", "Item not found in cart");
        }

        if (cart.getCartItems().size() <= 1) {
            throw new CartDomainException("cart", "Cart must have at least one item; cannot remove the last item");
        }

        cart.removeCartItem(deleteCardItemCommand.productId());
        return cartRepository.save(cart);
    }

}
