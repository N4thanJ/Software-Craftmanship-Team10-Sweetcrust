package com.sweetcrust.team10_bakery.domain.order;

import com.sweetcrust.team10_bakery.order.domain.OrderDomainException;
import com.sweetcrust.team10_bakery.order.domain.entities.OrderItem;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    void givenValidData_whenCreatingOrderItem_thenOrderItemIsCreated() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 5;
        BigDecimal unitPrice = BigDecimal.valueOf(3.50);

        // when
        OrderItem item = new OrderItem(productId, variantId, quantity, unitPrice);

        // then
        assertNotNull(item);
        assertNotNull(item.getOrderItemId());
        assertEquals(productId, item.getProductId());
        assertEquals(variantId, item.getVariantId());
        assertEquals(quantity, item.getQuantity());
        assertEquals(unitPrice, item.getUnitPrice());
    }

    @Test
    void givenNullProductId_whenCreatingOrderItem_thenThrowsException() {
        // given
        VariantId variantId = new VariantId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> new OrderItem(null, variantId, quantity, unitPrice));

        // then
        assertEquals("productId", exception.getField());
        assertEquals("productId must not be null", exception.getMessage());
    }

    @Test
    void givenNullVariantId_whenCreatingOrderItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> new OrderItem(productId, null, quantity, unitPrice));

        // then
        assertEquals("variantId", exception.getField());
        assertEquals("variantId must not be null", exception.getMessage());
    }

    @Test
    void givenNegativeQuantity_whenCreatingOrderItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        BigDecimal unitPrice = BigDecimal.valueOf(2.50);

        //w when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> new OrderItem(productId, variantId, -1, unitPrice));

        // then
        assertEquals("quantity", exception.getField());
        assertEquals("quantity must not be negative", exception.getMessage());
    }

    @Test
    void givenNullUnitPrice_whenCreatingOrderItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 3;

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> new OrderItem(productId, variantId, quantity, null));

        // then
        assertEquals("unitPrice", exception.getField());
        assertEquals("unitPrice must not be null", exception.getMessage());
    }

    @Test
    void givenNegativeUnitPrice_whenCreatingOrderItem_thenThrowsException() {
        // given
        ProductId productId = new ProductId();
        VariantId variantId = new VariantId();
        int quantity = 3;
        BigDecimal unitPrice = BigDecimal.valueOf(-2.50);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> new OrderItem(productId, variantId, quantity, unitPrice));

        // then
        assertEquals("unitPrice", exception.getField());
        assertEquals("unitPrice must not be negative", exception.getMessage());
    }
}
