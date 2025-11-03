package com.sweetcrust.team10_bakery.cart.application;

import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductVariantRepository;
import org.springframework.stereotype.Service;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;

import java.util.Optional;

@Service
public class CartCommandHandler {

    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;

    public CartCommandHandler(CartRepository cartRepository, ProductVariantRepository productVariantRepository) {
        this.cartRepository = cartRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public Cart createCart(CreateCartCommand command) {
        ProductVariant variant = getVariantOrThrow(command.variantId());

        CartItem cartItem = CartItem.fromVariant(variant, command.quantity());

        Cart cart = cartRepository.findByOwnerId(command.ownerId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setOwnerId(command.ownerId());
                    return newCart;
                });

        cart.addCartItem(cartItem);
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(AddItemToCartCommand command) {
        Cart cart = cartRepository.findByOwnerId(command.ownerId())
                .orElseThrow(() -> new CartServiceException("ownerId", "User does not have a cart"));

        ProductVariant variant = getVariantOrThrow(command.variantId());
        CartItem cartItem = CartItem.fromVariant(variant, command.quantity());

        cart.addCartItem(cartItem);
        return cartRepository.save(cart);
    }

    public Optional<Cart> removeItemFromCart(RemoveItemFromCartCommand cmd, CartItemId cartItemId) {
        Cart cart = cartRepository.findByOwnerId(cmd.ownerId())
                .orElseThrow(() -> new CartServiceException("ownerId", "User does not have a cart"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new CartServiceException("cartItemId", "Item does not exist"));

        if (cmd.quantity() >= cartItem.getQuantity()) {
            cart.removeCartItem(cartItemId);
        } else {
            cartItem.decreaseQuantity(cmd.quantity());
        }

        if (cart.isEmpty()) {
            cartRepository.delete(cart);
            return Optional.empty();
        }

        return Optional.of(cartRepository.save(cart));
    }

    private ProductVariant getVariantOrThrow(VariantId variantId) {
        return productVariantRepository.findById(variantId)
                .orElseThrow(() -> new CartServiceException("variantId", "Variant not found"));
    }
}
