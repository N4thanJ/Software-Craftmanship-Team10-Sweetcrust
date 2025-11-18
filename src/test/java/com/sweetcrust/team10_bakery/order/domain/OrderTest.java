package com.sweetcrust.team10_bakery.order.domain;

import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
                CartId cartId = new CartId();
                ShopId shopId = new ShopId();

                // when
                Order order = Order.createB2C(address, requestedDeliveryDate, customerId, cartId, shopId);

                // then
                assertNotNull(order);
                assertNotNull(order.getOrderId());
                assertEquals(OrderType.B2C, order.getOrderType());
                assertNull(order.getOrderingShopId());
                assertEquals(shopId, order.getSourceShopId());
                assertEquals(customerId, order.getCustomerId());
                assertEquals(cartId, order.getCartId());
                assertEquals(OrderStatus.PENDING, order.getStatus());
                assertEquals(address, order.getDeliveryAddress());
                assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
        }

        @Test
        void givenValidB2BData_whenCreatingOrder_thenOrderIsCreated() {
                // given
                LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(2);
                ShopId orderingShopId = new ShopId();
                ShopId sourceShopId = new ShopId();
                CartId cartId = new CartId();

                // when
                Order order = Order.createB2B(requestedDeliveryDate, orderingShopId, sourceShopId, cartId);

                // then
                assertNotNull(order);
                assertNotNull(order.getOrderId());
                assertEquals(OrderType.B2B, order.getOrderType());
                assertEquals(orderingShopId, order.getOrderingShopId());
                assertEquals(sourceShopId, order.getSourceShopId());
                assertNull(order.getCustomerId());
                assertEquals(cartId, order.getCartId());
                assertEquals(OrderStatus.PENDING, order.getStatus());
                assertNull(order.getDeliveryAddress());
                assertEquals(requestedDeliveryDate, order.getRequestedDeliveryDate());
        }

        @Test
        void givenNullDeliveryAddressForB2C_whenCreatingOrder_thenThrowsException() {
                // given
                UserId customerId = new UserId();
                CartId cartId = new CartId();
                ShopId shopId = new ShopId();
                LocalDateTime requestedDeliveryDate = LocalDateTime.now().plusDays(1);

                // when
                OrderDomainException exception = assertThrows(OrderDomainException.class,
                                () -> Order.createB2C(null, requestedDeliveryDate, customerId, cartId, shopId));

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
                CartId cartId = new CartId();
                ShopId shopId = new ShopId();

                // when
                OrderDomainException exception = assertThrows(OrderDomainException.class,
                                () -> Order.createB2C(address, null, customerId, cartId, shopId));

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
                CartId cartId = new CartId();
                ShopId shopId = new ShopId();

                // when
                OrderDomainException exception = assertThrows(OrderDomainException.class,
                                () -> Order.createB2C(address, requestedDeliveryDate, customerId, cartId, shopId));

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
                CartId cartId = new CartId();
                ShopId shopId = new ShopId();

                // when
                OrderDomainException exception = assertThrows(OrderDomainException.class,
                                () -> Order.createB2C(address, requestedDeliveryDate, null, cartId, shopId));

                // then
                assertEquals("customerId", exception.getField());
                assertEquals("customerId should not be null", exception.getMessage());
        }

}
