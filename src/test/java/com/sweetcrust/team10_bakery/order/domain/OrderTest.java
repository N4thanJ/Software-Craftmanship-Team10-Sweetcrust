package com.sweetcrust.team10_bakery.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {

  private Address defaultAddress;
  private UserId defaultCustomerId;
  private ShopId defaultSourceShopId;
  private ShopId defaultOrderingShopId;
  private CartId defaultCartId;
  private LocalDateTime defaultRequestedDate;
  private LocalDateTime defaultPastRequestedDate;

  @BeforeEach
  void setup() {
    defaultAddress =
        Address.builder()
            .setStreet("Default Street 1")
            .setCity("Default City")
            .setPostalCode("00000")
            .setCountry("Defaultland")
            .build();

    defaultCustomerId = new UserId();
    defaultSourceShopId = new ShopId();
    defaultOrderingShopId = new ShopId();
    defaultCartId = new CartId();

    defaultRequestedDate = LocalDateTime.now().plusDays(2);
    defaultPastRequestedDate = LocalDateTime.now().minusDays(2);
  }

  private Order createB2C() {
    return Order.createB2C(
        defaultAddress,
        defaultRequestedDate,
        defaultCustomerId,
        defaultCartId,
        defaultSourceShopId);
  }

  private Order createB2B() {
    return Order.createB2B(
        defaultRequestedDate, defaultOrderingShopId, defaultSourceShopId, defaultCartId);
  }

  private void expectFieldError(String field, String message, Runnable action) {
    OrderDomainException ex = assertThrows(OrderDomainException.class, action::run);
    assertEquals(field, ex.getField());
    assertEquals(message, ex.getMessage());
  }

  @Test
  void givenNullOrderType_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError("orderType", "orderType should not be null", () -> order.setOrderType(null));
  }

  @Test
  void givenNullDeliveryAddress_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "deliveryAddress",
        "deliveryAddress should not be null",
        () -> order.setDeliveryAddress(null));
  }

  @Test
  void givenNullRequestedDeliveryDate_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "requestedDeliveryDate",
        "requestedDeliveryDate should not be null",
        () -> order.setRequestedDeliveryDate(null));
  }

  @Test
  void givenPastRequestedDeliveryDate_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "requestedDeliveryDate",
        "requestedDeliveryDate should be after orderDate",
        () -> order.setRequestedDeliveryDate(defaultPastRequestedDate));
  }

  @Test
  void givenNullCustomerId_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "customerId", "customerId should not be null", () -> order.setCustomerId(null));
  }

  @Test
  void givenNullOrderingShopId_whenSetting_thenThrowsException() {
    Order order = createB2B();
    expectFieldError(
        "orderingShopId", "orderingShopId should not be null", () -> order.setOrderingShopId(null));
  }

  @Test
  void givenNullSourceShopId_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "sourceShopId", "sourceShopId should not be null", () -> order.setSourceShopId(null));
  }

  @Test
  void givenNullCartId_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError("cartId", "cartId should not be null", () -> order.setCartId(null));
  }

  @Test
  void givenNegativeSubtotal_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "subtotal", "subtotal cannot be negative", () -> order.setSubtotal(BigDecimal.valueOf(-1)));
  }

  @Test
  void givenNegativeTotalAfterDiscount_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "totalAfterDiscount",
        "totalAfterDiscount cannot be negative",
        () -> order.setTotalAfterDiscount(BigDecimal.valueOf(-0.01)));
  }

  @Test
  void givenInvalidDiscountRate_whenSetting_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "discountRate",
        "discountRate must be between 0 and 1",
        () -> order.setDiscountRate(BigDecimal.valueOf(-0.01)));
    expectFieldError(
        "discountRate",
        "discountRate must be between 0 and 1",
        () -> order.setDiscountRate(BigDecimal.valueOf(1.01)));
  }

  @Test
  void givenNonPendingOrder_whenConfirming_thenThrowsException() {
    Order order = createB2C();
    order.confirm();
    expectFieldError("status", "Only pending orders can be confirmed", order::confirm);
  }

  @Test
  void givenNonConfirmedOrder_whenMarkingShipped_thenThrowsException() {
    Order order = createB2C();
    expectFieldError(
        "status", "Only confirmed orders can be marked as shipped", order::markShipped);
  }

  @Test
  void givenNonShippedOrder_whenDelivering_thenThrowsException() {
    Order order = createB2C();
    expectFieldError("status", "Only shipped orders can be delivered", order::deliver);
  }

  @Test
  void givenShippedOrDeliveredOrder_whenCancelling_thenThrowsException() {
    Order order = createB2C();
    order.confirm();
    order.markShipped();
    expectFieldError("status", "Shipped or Delivered orders cannot be cancelled", order::cancel);
  }

  @Test
  void givenTooLateCancellation_whenCancelling_thenThrowsException() {
    Order order = createB2C();
    order.setRequestedDeliveryDate(LocalDateTime.now().plusHours(10));
    expectFieldError(
        "status",
        "Orders can only be cancelled up until 1 day before the requested delivery date",
        order::cancel);
  }
}
