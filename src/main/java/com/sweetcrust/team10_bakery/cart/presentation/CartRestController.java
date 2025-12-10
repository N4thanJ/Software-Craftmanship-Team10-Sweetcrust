package com.sweetcrust.team10_bakery.cart.presentation;

import com.sweetcrust.team10_bakery.cart.application.CartCommandHandler;
import com.sweetcrust.team10_bakery.cart.application.CartQueryHandler;
import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.cart.presentation.dto.CartResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(
    name = "Cart Management",
    description = "Endpoints related to adding and removing items from carts")
public class CartRestController {
  private final CartQueryHandler cartQueryHandler;
  private final CartCommandHandler cartCommandHandler;

  public CartRestController(
      CartQueryHandler cartQueryHandler, CartCommandHandler cartCommandHandler) {
    this.cartQueryHandler = cartQueryHandler;
    this.cartCommandHandler = cartCommandHandler;
  }

  @GetMapping()
  public ResponseEntity<Iterable<CartResponse>> getAllCarts() {
    List<CartResponse> carts = cartQueryHandler.getAllCarts();
    return ResponseEntity.ok(carts);
  }

  @PostMapping()
  public ResponseEntity<Cart> createCart(@RequestBody CreateCartCommand createCartCommand) {
    Cart cart = cartCommandHandler.createCart(createCartCommand);
    return ResponseEntity.ok(cart);
  }

  @PutMapping
  public ResponseEntity<Cart> addItemToCart(
      @RequestBody AddItemToCartCommand addItemToCartCommand) {
    Cart cart = cartCommandHandler.addItemToCart(addItemToCartCommand);
    return ResponseEntity.ok(cart);
  }

  @DeleteMapping("/{cartItemId}")
  public ResponseEntity<Cart> removeItemFromCart(
      @RequestBody RemoveItemFromCartCommand removeItemFromCartCommand,
      @PathVariable CartItemId cartItemId) {

    return cartCommandHandler
        .removeItemFromCart(removeItemFromCartCommand, cartItemId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }
}
