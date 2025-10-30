package com.sweetcrust.team10_bakery.order.domain.entities;

import java.time.LocalDateTime;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.order.domain.OrderDomainException;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @EmbeddedId
    private OrderId orderId;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "ordering_shop_id"))
    private ShopId orderingShopId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "source_shop_id"))
    private ShopId sourceShopId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "customer_id"))
    private UserId customerId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "cart_id"))
    private CartId cartId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address deliveryAddress;

    private LocalDateTime orderDate;

    private LocalDateTime requestedDeliveryDate;

    protected Order() {
    }

    // Factory METHOD PATTERN
    private Order(LocalDateTime requestedDeliveryDate) {
        this.orderId = new OrderId();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        setRequestedDeliveryDate(requestedDeliveryDate);
    }

    // B2C orders (klant order)
    public static Order createB2C(Address deliveryAddress, LocalDateTime requestedDeliveryDate, UserId customerId,
            CartId cartId) {
        Order order = new Order(requestedDeliveryDate);
        order.setOrderType(OrderType.B2C);
        order.setDeliveryAddress(deliveryAddress);
        order.setCustomerId(customerId);
        order.setCartId(cartId);
        return order;
    }

    // B2B orders (SweetCrust order bij andere SweetCrust)
    public static Order createB2B(LocalDateTime requestedDeliveryDate, ShopId orderingShopId, ShopId sourceShopId,
            CartId cartId) {
        Order order = new Order(requestedDeliveryDate);
        order.setOrderType(OrderType.B2B);
        order.setOrderingShopId(orderingShopId);
        order.setSourceShopId(sourceShopId);
        order.setCartId(cartId);
        return order;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public UserId getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public ShopId getSourceShopId() {
        return sourceShopId;
    }

    public ShopId getOrderingShopId() {
        return orderingShopId;
    }

    public CartId getCartId() {
        return cartId;
    }

    public void setOrderType(OrderType orderType) {
        if (orderType == null) {
            throw new OrderDomainException("orderType", "orderType should not be null");
        }
        this.orderType = orderType;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        if (deliveryAddress == null) {
            throw new OrderDomainException("deliveryAddress", "deliveryAddress should not be null");
        }
        this.deliveryAddress = deliveryAddress;
    }

    public void setRequestedDeliveryDate(LocalDateTime requestedDeliveryDate) {
        if (requestedDeliveryDate == null) {
            throw new OrderDomainException("requestedDeliveryDate", "requestedDeliveryDate should not be null");
        }
        if (!requestedDeliveryDate.isAfter(orderDate)) {
            throw new OrderDomainException("requestedDeliveryDate", "requestedDeliveryDate should be after orderDate");
        }
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public void setCustomerId(UserId customerId) {
        if (customerId == null) {
            throw new OrderDomainException("customerId", "customerId should not be null");
        }
        this.customerId = customerId;
    }

    public void setOrderingShopId(ShopId orderingShopId) {
        if (orderingShopId == null) {
            throw new OrderDomainException("orderingShopId", "orderingShopId should not be null");
        }
        this.orderingShopId = orderingShopId;
    }

    public void setSourceShopId(ShopId sourceShopId) {
        if (sourceShopId == null) {
            throw new OrderDomainException("sourceShopId", "sourceShopId should not be null");
        }
        this.sourceShopId = sourceShopId;
    }

    public void setCartId(CartId cartId) {
        if (cartId == null) {
            throw new OrderDomainException("cartId", "cartId should not be null");
        }
        this.cartId = cartId;
    }

    public void validateOrder() {
        if (orderType == OrderType.B2C && customerId == null) {
            throw new OrderDomainException("customerId", "B2C orders must have customerId");
        }
        if (orderType == OrderType.B2B && orderingShopId == null) {
            throw new OrderDomainException("orderingShopId", "B2B orders must have orderingShopId");
        }
        if (cartId == null) {
            throw new OrderDomainException("cartId", "cartId should not be empty");
        }
    }

    public void confirm() {
        validateOrder();
        if (status != OrderStatus.PENDING) {
            throw new OrderDomainException("status", "Only pending orders can be confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void startPreparing() {
        if (status != OrderStatus.CONFIRMED) {
            throw new OrderDomainException("status", "Only confirmed orders can be prepared");
        }
        this.status = OrderStatus.PREPARING;
    }

    public void markReady() {
        if (status != OrderStatus.PREPARING) {
            throw new OrderDomainException("status", "Only preparing orders can be marked as ready");
        }
        this.status = OrderStatus.READY;
    }

    public void deliver() {
        if (status != OrderStatus.READY) {
            throw new OrderDomainException("status", "Only ready orders can be delivered");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new OrderDomainException("status", "Delivered orders cannot be cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }
}