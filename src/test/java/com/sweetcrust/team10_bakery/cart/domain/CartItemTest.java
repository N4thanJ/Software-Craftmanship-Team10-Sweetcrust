package com.sweetcrust.team10_bakery.cart.domain;

import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    @Test
    void givenValidData_whenCreatingCartItem_thenCartItemIsCreated() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 5;
        BigDecimal unitPrice = BigDecimal.valueOf(3.50);

        // when
        CartItem item = new CartItem(productId, variantId, quantity, unitPrice);

        // then
        assertNotNull(item);
        assertNotNull(item.getCartItemId());
        assertEquals(productId, item.getProductId());
        assertEquals(variantId, item.getVariantId());
        assertEquals(quantity, item.getQuantity());
        assertEquals(unitPrice, item.getUnitPrice());
    }

    @Test
    void givenNullProductId_whenCreatingCartItem_thenThrowsException() {
        // given
        VariantId variantId = new VariantId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        // when
        CartDomainException exception = assertThrows(CartDomainException.class,
                () -> new CartItem(null, variantId, quantity, unitPrice));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("productId must not be null", exception.getMessage());
    }

    @Test
    void givenNullVariantId_whenCreatingCartItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        // when
        CartDomainException exception = assertThrows(CartDomainException.class,
                () -> new CartItem(productId, null, quantity, unitPrice));

        // then
        assertEquals("variantId", exception.getField());
        assertEquals("variantId must not be null", exception.getMessage());
    }

    @Test
    void givenNegativeQuantity_whenCreatingCartItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        // w when
        CartDomainException exception = assertThrows(CartDomainException.class,
                () -> new CartItem(productId, variantId, -1, unitPrice));

        // then
        assertEquals("quantity", exception.getField());
        assertEquals("quantity must be positive", exception.getMessage());
    }

    @Test
    void givenNullUnitPrice_whenCreatingCartItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 3;

        // when
        CartDomainException exception = assertThrows(CartDomainException.class,
                () -> new CartItem(productId, variantId, quantity, null));

        // then
        assertEquals("unitPrice", exception.getField());
        assertEquals("unitPrice must not be null", exception.getMessage());
    }

    @Test
    void givenNegativeUnitPrice_whenCreatingCartItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(-2.50);

        // when
        CartDomainException exception = assertThrows(CartDomainException.class,
                () -> new CartItem(productId, variantId, quantity, unitPrice));

        // then
        assertEquals("unitPrice", exception.getField());
        assertEquals("unitPrice must not be negative", exception.getMessage());
    }
}
