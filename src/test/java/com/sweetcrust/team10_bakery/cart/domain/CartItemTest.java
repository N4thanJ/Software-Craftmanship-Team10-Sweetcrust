package com.sweetcrust.team10_bakery.cart.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartItemTest {

  private CartId defaultCartId;
  private ProductVariant defaultVariant;
  private VariantId defaultVariantId;
  private BigDecimal defaultPrice;
  private int defaultQuantity;

  @BeforeEach
  void setup() {
    defaultCartId = new CartId();
    defaultVariant = mock(ProductVariant.class);
    defaultVariantId = new VariantId();
    defaultPrice = BigDecimal.valueOf(3.50);
    defaultQuantity = 2;

    when(defaultVariant.getVariantId()).thenReturn(defaultVariantId);
    when(defaultVariant.getPrice()).thenReturn(defaultPrice);
  }

  private void expectFieldError(String message, Runnable action) {
    CartDomainException ex = assertThrows(CartDomainException.class, action::run);
    assertEquals("quantity", ex.getField());
    assertEquals(message, ex.getMessage());
  }

  private CartItem createDefaultItem() {
    return CartItem.fromVariant(defaultCartId, defaultVariant, defaultQuantity);
  }

  @Test
  void givenValidData_whenCreatingCartItemFromVariant_thenCartItemIsCreated() {
    // when
    CartItem cartItem = createDefaultItem();

    // then
    assertNotNull(cartItem);
    assertNotNull(cartItem.getCartItemId());
    assertEquals(defaultVariantId, cartItem.getVariantId());
    assertEquals(defaultQuantity, cartItem.getQuantity());
    assertEquals(defaultPrice, cartItem.getUnitPrice());
    assertEquals(
        defaultPrice.multiply(BigDecimal.valueOf(defaultQuantity)), cartItem.getTotalPrice());
  }

  @Test
  void givenZeroQuantity_whenCreatingCartItem_thenThrowsException() {
    expectFieldError(
        "quantity must be positive", () -> CartItem.fromVariant(defaultCartId, defaultVariant, 0));
  }

  @Test
  void givenNegativeQuantity_whenCreatingCartItem_thenThrowsException() {
    expectFieldError(
        "quantity must be positive", () -> CartItem.fromVariant(defaultCartId, defaultVariant, -1));
  }

  @Test
  void givenValidCartItem_whenIncreasingQuantity_thenQuantityIncreases() {
    CartItem cartItem = createDefaultItem();

    // when
    cartItem.increaseQuantity(3);

    // then
    assertEquals(defaultQuantity + 3, cartItem.getQuantity());
  }

  @Test
  void givenInvalidAmount_whenIncreasingQuantity_thenThrowsException() {
    CartItem cartItem = createDefaultItem();

    expectFieldError("amount must be positive", () -> cartItem.increaseQuantity(0));
  }

  @Test
  void givenValidCartItem_whenDecreasingQuantity_thenQuantityDecreases() {
    CartItem cartItem = createDefaultItem();

    // when
    cartItem.decreaseQuantity(1);

    // then
    assertEquals(defaultQuantity - 1, cartItem.getQuantity());
  }

  @Test
  void givenDecreaseAmountGreaterThanQuantity_whenDecreasing_thenThrowsException() {
    CartItem cartItem = createDefaultItem();

    expectFieldError(
        "quantity must be greater than or equal to amount",
        () -> cartItem.decreaseQuantity(defaultQuantity + 1));
  }

  @Test
  void givenDecreaseAmountEqualToQuantity_whenDecreasing_thenQuantityBecomesZero() {
    CartItem cartItem = createDefaultItem();

    // when
    cartItem.decreaseQuantity(defaultQuantity);

    // then
    assertEquals(0, cartItem.getQuantity());
  }

  @Test
  void givenDifferentVariantId_whenCheckingIsSameVariant_thenReturnsFalse() {
    CartItem item = createDefaultItem();

    assertFalse(item.isSameVariant(new VariantId()));
  }

  @Test
  void givenCartItem_whenIncreasingQuantity_thenTotalPriceUpdatesCorrectly() {
    CartItem item = createDefaultItem();

    item.increaseQuantity(3);

    assertEquals(
        defaultPrice.multiply(BigDecimal.valueOf(defaultQuantity + 3)), item.getTotalPrice());
  }

  @Test
  void givenCartItem_whenDecreasingQuantity_thenTotalPriceUpdatesCorrectly() {
    CartItem item = createDefaultItem();

    item.decreaseQuantity(1);

    assertEquals(
        defaultPrice.multiply(BigDecimal.valueOf(defaultQuantity - 1)), item.getTotalPrice());
  }
}
