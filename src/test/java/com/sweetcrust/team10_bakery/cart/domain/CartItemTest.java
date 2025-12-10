package com.sweetcrust.team10_bakery.cart.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CartItemTest {

  @Test
  void givenValidData_whenCreatingCartItemFromVariant_thenCartItemIsCreated() {
    VariantId variantId = new VariantId();
    ProductVariant chocolateCroissant = mock(ProductVariant.class);
    when(chocolateCroissant.getVariantId()).thenReturn(variantId);
    when(chocolateCroissant.getPrice()).thenReturn(BigDecimal.valueOf(3.50));

    int quantity = 2;

    CartItem cartItem = CartItem.fromVariant(new CartId(), chocolateCroissant, quantity);

    assertNotNull(cartItem);
    assertNotNull(cartItem.getCartItemId());
    assertEquals(variantId, cartItem.getVariantId());
    assertEquals(quantity, cartItem.getQuantity());
    assertEquals(BigDecimal.valueOf(3.50), cartItem.getUnitPrice());
    assertEquals(BigDecimal.valueOf(7.00), cartItem.getTotalPrice());
  }

  @Test
  void givenNullVariant_whenCreatingCartItem_thenThrowsException() {
    CartDomainException exception =
        assertThrows(CartDomainException.class, () -> CartItem.fromVariant(new CartId(), null, 2));
    assertEquals("variant", exception.getField());
    assertEquals("variant must not be null", exception.getMessage());
  }

  @Test
  void givenZeroQuantity_whenCreatingCartItem_thenThrowsException() {
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(new VariantId());
    when(variant.getPriceModifier()).thenReturn(BigDecimal.valueOf(2.00));

    CartDomainException exception =
        assertThrows(
            CartDomainException.class, () -> CartItem.fromVariant(new CartId(), variant, 0));
    assertEquals("quantity", exception.getField());
    assertEquals("quantity must be positive", exception.getMessage());
  }

  @Test
  void givenNegativeQuantity_whenCreatingCartItem_thenThrowsException() {
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(new VariantId());
    when(variant.getPriceModifier()).thenReturn(BigDecimal.valueOf(2.00));

    CartDomainException exception =
        assertThrows(
            CartDomainException.class, () -> CartItem.fromVariant(new CartId(), variant, -3));
    assertEquals("quantity", exception.getField());
    assertEquals("quantity must be positive", exception.getMessage());
  }

  @Test
  void givenValidCartItem_whenIncreasingQuantity_thenQuantityIncreases() {
    ProductVariant muffinVariant = mock(ProductVariant.class);
    when(muffinVariant.getVariantId()).thenReturn(new VariantId());
    when(muffinVariant.getPrice()).thenReturn(BigDecimal.valueOf(4.00));

    CartItem cartItem = CartItem.fromVariant(new CartId(), muffinVariant, 3);
    cartItem.increaseQuantity(2);

    assertEquals(5, cartItem.getQuantity());
  }

  @Test
  void givenInvalidAmount_whenIncreasingQuantity_thenThrowsException() {
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(new VariantId());
    when(variant.getPrice()).thenReturn(BigDecimal.valueOf(4.00));

    CartItem cartItem = CartItem.fromVariant(new CartId(), variant, 3);

    CartDomainException exception =
        assertThrows(CartDomainException.class, () -> cartItem.increaseQuantity(0));
    assertEquals("quantity", exception.getField());
    assertEquals("amount must be positive", exception.getMessage());
  }

  @Test
  void givenValidCartItem_whenDecreasingQuantity_thenQuantityDecreases() {
    ProductVariant brownieVariant = mock(ProductVariant.class);
    when(brownieVariant.getVariantId()).thenReturn(new VariantId());
    when(brownieVariant.getPrice()).thenReturn(BigDecimal.valueOf(2.50));

    CartItem cartItem = CartItem.fromVariant(new CartId(), brownieVariant, 5);
    cartItem.decreaseQuantity(2);

    assertEquals(3, cartItem.getQuantity());
  }

  @Test
  void givenDecreaseAmountGreaterThanQuantity_whenDecreasing_thenThrowsException() {
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(new VariantId());
    when(variant.getPrice()).thenReturn(BigDecimal.valueOf(2.50));

    CartItem cartItem = CartItem.fromVariant(new CartId(), variant, 2);

    CartDomainException exception =
        assertThrows(CartDomainException.class, () -> cartItem.decreaseQuantity(3));
    assertEquals("quantity", exception.getField());
    assertEquals("quantity must be greater than or equal to amount", exception.getMessage());
  }

  @Test
  void givenTwoCartItemsWithSameVariant_whenCompared_thenTheyAreEqual() {
    VariantId sharedId = new VariantId();
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(sharedId);
    when(variant.getPrice()).thenReturn(BigDecimal.valueOf(5.00));

    CartItem item1 = CartItem.fromVariant(new CartId(), variant, 3);
    CartItem item2 = CartItem.fromVariant(new CartId(), variant, 10);

    assertEquals(item1, item2);
    assertEquals(item1.hashCode(), item2.hashCode());
  }

  @Test
  void givenDifferentVariants_whenCompared_thenTheyAreNotEqual() {
    ProductVariant v1 = mock(ProductVariant.class);
    ProductVariant v2 = mock(ProductVariant.class);
    when(v1.getVariantId()).thenReturn(new VariantId());
    when(v1.getPrice()).thenReturn(BigDecimal.valueOf(2.50));
    when(v2.getVariantId()).thenReturn(new VariantId());
    when(v2.getPrice()).thenReturn(BigDecimal.valueOf(2.50));

    CartItem item1 = CartItem.fromVariant(new CartId(), v1, 3);
    CartItem item2 = CartItem.fromVariant(new CartId(), v2, 3);

    assertNotEquals(item1, item2);
  }

  @Test
  void givenVariantId_whenCheckingIsSameVariant_thenReturnsTrueIfSame() {
    VariantId sharedId = new VariantId();
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(sharedId);
    when(variant.getPrice()).thenReturn(BigDecimal.valueOf(3.50));

    CartItem muffinItem = CartItem.fromVariant(new CartId(), variant, 4);
    assertTrue(muffinItem.isSameVariant(sharedId));
  }

  @Test
  void givenDifferentVariantId_whenCheckingIsSameVariant_thenReturnsFalse() {
    ProductVariant variant = mock(ProductVariant.class);
    when(variant.getVariantId()).thenReturn(new VariantId());
    when(variant.getPrice()).thenReturn(BigDecimal.valueOf(3.50));

    CartItem donutItem = CartItem.fromVariant(new CartId(), variant, 4);
    assertFalse(donutItem.isSameVariant(new VariantId()));
  }
}
