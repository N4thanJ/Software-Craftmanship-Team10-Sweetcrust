package com.sweetcrust.team10_bakery.cart.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweetcrust.team10_bakery.cart.application.CartCommandService;
import com.sweetcrust.team10_bakery.cart.application.CartQueryService;
import com.sweetcrust.team10_bakery.cart.application.commands.AddCartItemCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.CreateCartCommand;
import com.sweetcrust.team10_bakery.cart.application.commands.DeleteCartItemCommand;
import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

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

    @PutMapping("/addItem/{cartId}")
    public ResponseEntity<Cart> addCartItemToCart(@PathVariable CartId cartId,
            @RequestBody AddCartItemCommand addCardItemCommand) {
        Cart cart = cartCommandService.addCardItem(cartId, addCardItemCommand);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/removeItem/{cardId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable CartId cartId,
            @RequestBody DeleteCartItemCommand deleteCardItemCommand) {
        Cart cart = cartCommandService.removeCardItem(cartId, deleteCardItemCommand);
        return ResponseEntity.ok(cart);
    }

}
