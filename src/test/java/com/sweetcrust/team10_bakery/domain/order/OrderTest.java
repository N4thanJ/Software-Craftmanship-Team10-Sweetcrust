package com.sweetcrust.team10_bakery.domain.order;

import com.sweetcrust.team10_bakery.order.domain.OrderDomainException;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.entities.OrderItem;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.DeliveryAddress;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OrderTest {

    @Test
    void givenValidB2CData_whenCreatingOrder_thenOrderIsCreated() {
        // given
        DeliveryAddress address = new DeliveryAddress("Bakery Lane 13", "Donutville", "12345", "Sweetland");
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        Order order = Order.createB2C(OrderType.B2C, address, requestedDeliveryDate, customerId);

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
        String orderingBranchId = "SweetCrust Wonderland";
        String sourceBranchId = "SweetCrust Candyland";

        // when
        Order order = Order.createB2B(OrderType.B2B, requestedDeliveryDate, orderingBranchId, sourceBranchId);

        // then
        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals(OrderType.B2B, order.getOrderType());
        assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
    }

    @Test
    void givenNullDeliveryAddressForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(OrderType.B2C, null, requestedDeliveryDate, customerId));

        // then
        assertEquals("deliveryAddress", exception.getField());
        assertEquals("deliveryAddress should not be null", exception.getMessage());
    }

    @Test
    void givenNullRequestedDeliveryDateForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        DeliveryAddress address = new DeliveryAddress("Cupcake Crescent 99", "Frostingtown", "54321", "Sweetland");
        UserId customerId = new UserId();

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(OrderType.B2C, address, null, customerId));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("requestedDeliveryDate should not be null", exception.getMessage());
    }

    @Test
    void givenRequestedDeliveryDateBeforeOrderDate_whenCreatingB2COrder_thenThrowsException() {
        // given
        DeliveryAddress address = new DeliveryAddress("Pie Street 7", "Tartville", "11111", "Sweetland");
        UserId customerId = new UserId();
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().minusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(OrderType.B2C, address, requestedDeliveryDate, customerId));

        // then
        assertEquals("requestedDeliveryDate", exception.getField());
        assertEquals("requestedDeliveryDate should be after orderDate", exception.getMessage());
    }

    @Test
    void givenNullCustomerIdForB2C_whenCreatingOrder_thenThrowsException() {
        // given
        DeliveryAddress address = new DeliveryAddress("Muffin Avenue 42", "Bakerville", "67890", "Sweetland");
        LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                () -> Order.createB2C(OrderType.B2C, address, requestedDeliveryDate, null));

        // then
        assertEquals("customerId", exception.getField());
        assertEquals("customerId should not be null", exception.getMessage());
    }

    @Test
    void givenNullOrderItem_whenAddingOrderItem_thenThrowsException() {
        // given
        Order order = Order.createB2C(OrderType.B2C,
                new DeliveryAddress("Bread Boulevard 1", "Loaf City", "10101", "Sweetland"),
                LocalDateTime.now().plusDays(1),
                new UserId()
        );

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
        Order order = Order.createB2C(OrderType.B2C,
                new DeliveryAddress("Bagel Boulevard 17", "Cheesetown", "20202", "Sweetland"),
                LocalDateTime.now().plusDays(1),
                new UserId()
        );

        // when
        OrderItem item = mock(OrderItem.class);
        order.addOrderItem(item);
        order.removeOrderItem(item);

        // then
        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    void givenEmptyOrder_whenValidatingOrder_thenThrowsException() {
        // given
        Order order = Order.createB2C(OrderType.B2C,
                new DeliveryAddress("Croissant Court 5", "Pastryville", "30303", "Sweetland"),
                LocalDateTime.now().plusDays(1),
                new UserId()
        );

        // when
        OrderDomainException exception = assertThrows(OrderDomainException.class,
                order::validateOrder);

        // then
        assertEquals("orderItems", exception.getField());
        assertEquals("orderItems should not be empty", exception.getMessage());
    }
}
