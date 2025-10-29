package com.sweetcrust.team10_bakery.cart.presentation;

import com.sweetcrust.team10_bakery.cart.application.commands.AddItemToCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.RemoveItemFromCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import org.springframework.web.bind.annotation.*;

import com.sweetcrust.team10_bakery.cart.application.CartCommandService;
import com.sweetcrust.team10_bakery.cart.application.CartQueryService;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart Management", description = "Endpoints related to adding and removing items from carts")
public class CartRestController {
    private final CartQueryService cartQueryService;
    private final CartCommandService cartCommandService;

    public CartRestController(CartQueryService cartQueryService, CartCommandService cartCommandService) {
        this.cartQueryService = cartQueryService;
        this.cartCommandService = cartCommandService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Cart>> getAllCarts() {
        List<Cart> carts = cartQueryService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @PostMapping()
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartCommand createCartCommand) {
        Cart cart = cartCommandService.createCart(createCartCommand);
        return ResponseEntity.ok(cart);
    }

    @PutMapping
    public ResponseEntity<Cart> addItemToCart(@RequestBody AddItemToCartCommand addItemToCartCommand) {
        Cart cart = cartCommandService.addItemToCart(addItemToCartCommand);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@RequestBody RemoveItemFromCartCommand removeItemFromCartCommand, @PathVariable CartItemId cartItemId) {

        return cartCommandService.removeItemFromCart(removeItemFromCartCommand, cartItemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
