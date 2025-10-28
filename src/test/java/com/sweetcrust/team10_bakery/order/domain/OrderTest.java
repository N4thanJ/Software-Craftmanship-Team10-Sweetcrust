package com.sweetcrust.team10_bakery.order.domain;

import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OrderTest {

    @Test
    void givenValidB2CData_whenCreatingOrder_thenOrderIsCreated() {
        // given
        Address address = Address.builder()
                .setStreet("Bakery Lane 13")
                .setCity("Donutville")
                .setPostalCode("12345")
                .setCountry("Sweetland")
                .build();
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        Order order = Order.createB2C(address, requestedDeliveryDate, customerId);

        // then
        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals(OrderType.B2C, order.getOrderType());
        assertEquals(address, order.getDeliveryAddress());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
    }

    @Test
    void givenValidB2BData_whenCreatingOrder_thenOrderIsCreated() {
        // given
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(2);
        ShopId orderingShopId = new ShopId();
        ShopId sourceShopId = new ShopId();

        // when
        Order order = Order.createB2B(requestedDeliveryDate, orderingShopId, sourceShopId);

        // then
        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals(OrderType.B2B, order.getOrderType());
        assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
        assertEquals(orderingShopId, order.getOrderingShopId());
        assertEquals(sourceShopId, order.getSourceShopId());
    }

    @Test
    void givenNullDeliveryAddressForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(null, requestedDeliveryDate, customerId));

        // then
        assertEquals("deliveryAddress", exception.getField());
        assertEquals("deliveryAddress should not be null", exception.getMessage());
    }

    @Test
    void givenNullRequestedDeliveryDateForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        Address address = Address.builder()
                .setStreet("Cupcake Crescent 99")
                .setCity("Frostingtown")
                .setPostalCode("54321")
                .setCountry("Sweetland")
                .build();
        UserId customerId = new UserId();

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(address, null, customerId));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("requestedDeliveryDate should not be null", exception.getMessage());
    }

    @Test
    void givenRequestedDeliveryDateBeforeOrderDate_whenCreatingB2COrder_thenThrowsException() {
        // given
        Address address = Address.builder()
                .setStreet("Pie Street 7")
                .setCity("Tartville")
                .setPostalCode("11111")
                .setCountry("Sweetland")
                .build();
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().minusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(address, requestedDeliveryDate, customerId));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("requestedDeliveryDate should be after orderDate", exception.getMessage());
    }

    @Test
    void givenNullCustomerIdForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        Address address = Address.builder()
                .setStreet("Muffin Avenue 42")
                .setCity("Bakerville")
                .setPostalCode("67890")
                .setCountry("Sweetland")
                .build();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(address, requestedDeliveryDate, null));

        // then
        assertEquals("customerId", exception.getField());
        assertEquals("customerId should not be null", exception.getMessage());
    }

    @Test
    void givenNullOrderItem_whenAddingOrderItem_thenThrowsException() {
        // given
        Order order = Order.createB2C(
                Address.builder()
                        .setStreet("Bread Boulevard 1")
                        .setCity("Loaf City")
                        .setPostalCode("10101")
                        .setCountry("Sweetland")
                        .build(),
                LocalDateTime.now().plusDays(1),
                new UserId());

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> order.addOrderItem(null));

        // then
        assertEquals("orderItem", exception.getField());
        assertEquals("orderItem should not be null", exception.getMessage());
    }

    @Test
    void givenOrderWithItems_whenRemovingOrderItem_thenItemIsRemoved() {
        // given
        Order order = Order.createB2C(
                Address.builder()
                        .setStreet("Bagel Boulevard 17")
                        .setCity("Cheesetown")
                        .setPostalCode("20202")
                        .setCountry("Sweetland")
                        .build(),
                LocalDateTime.now().plusDays(1),
                new UserId());

        // when
        CartItem item = mock(CartItem.class);
        order.addOrderItem(item);
        order.removeOrderItem(item);

        // then
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    void givenEmptyOrder_whenValidatingOrder_thenThrowsException() {
        // given
        Order order = Order.createB2C(
                Address.builder()
                        .setStreet("Croissant Court 5")
                        .setCity("Pastryville")
                        .setPostalCode("30303")
                        .setCountry("Sweetland")
                        .build(),
                LocalDateTime.now().plusDays(1),
                new UserId());

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                order::validateOrder);

        // then
        assertEquals("orderItems", exception.getField());
        assertEquals("orderItems should not be empty", exception.getMessage());
    }
}
