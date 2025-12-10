package com.sweetcrust.team10_bakery.cart.application;

import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartItemRepository;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductVariantRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CartCommandHandler {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductVariantRepository productVariantRepository;

  public CartCommandHandler(
      CartRepository cartRepository,
      CartItemRepository cartItemRepository,
      ProductVariantRepository productVariantRepository) {
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.productVariantRepository = productVariantRepository;
  }

  public Cart createCart(CreateCartCommand command) {
    ProductVariant variant = getVariantOrThrow(command.variantId());

    Product product = variant.getProduct();
    if (!product.isAvailable()) {
      throw new CartServiceException("product", "product is currently not available");
    }

    Cart cart =
        cartRepository
            .findByOwnerId(command.ownerId())
            .orElseGet(
                () -> {
                  Cart newCart = new Cart();
                  newCart.setOwnerId(command.ownerId());
                  return newCart;
                });

    CartItem cartItem = CartItem.fromVariant(cart.getCartId(), variant, command.quantity());

    cartItemRepository.save(cartItem);
    return cartRepository.save(cart);
  }

  public Cart addItemToCart(AddItemToCartCommand command) {
    Cart cart =
        cartRepository
            .findByOwnerId(command.ownerId())
            .orElseThrow(() -> new CartServiceException("ownerId", "User does not have a cart"));

    ProductVariant variant = getVariantOrThrow(command.variantId());
    Product product = variant.getProduct();
    if (!product.isAvailable()) {
      throw new CartServiceException("product", "product is currently not available");
    }
    CartItem cartItem = CartItem.fromVariant(cart.getCartId(), variant, command.quantity());

    cartItemRepository.save(cartItem);
    return cartRepository.save(cart);
  }

  public Optional<Cart> removeItemFromCart(RemoveItemFromCartCommand cmd, CartItemId cartItemId) {
    Cart cart =
        cartRepository
            .findByOwnerId(cmd.ownerId())
            .orElseThrow(() -> new CartServiceException("ownerId", "User does not have a cart"));

    CartItem cartItem =
        cartItemRepository
            .getCartItemByCartItemId(cartItemId)
            .orElseThrow(() -> new CartServiceException("cartItemId", "Item does not exist"));

    if (cmd.quantity() >= cartItem.getQuantity()) {
      cartItemRepository.delete(cartItem);
    } else {
      cartItem.decreaseQuantity(cmd.quantity());
      cartItemRepository.save(cartItem);
    }

    if (cartItemRepository.findByCartId(cart.getCartId()).isEmpty()) {
      cartRepository.delete(cart);
      return Optional.empty();
    }

    return Optional.of(cartRepository.save(cart));
  }

  private ProductVariant getVariantOrThrow(VariantId variantId) {
    return productVariantRepository
        .findById(variantId)
        .orElseThrow(() -> new CartServiceException("variantId", "Variant not found"));
  }
}
