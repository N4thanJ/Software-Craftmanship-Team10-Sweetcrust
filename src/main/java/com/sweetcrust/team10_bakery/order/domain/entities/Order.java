package com.sweetcrust.team10_bakery.order.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address deliveryAddress;

    private LocalDateTime orderDate;

    private LocalDateTime requestedDeliveryDate;

    protected Order() {
    }

    // Factory constructor (private cause only used inside the class)
    private Order(OrderType orderType, LocalDateTime requestedDeliveryDate) {
        this.orderId = new OrderId();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        setOrderType(orderType);
        setRequestedDeliveryDate(requestedDeliveryDate);
    }

    // B2C orders (klant order)
    public static Order createB2C(OrderType orderType, Address deliveryAddress, LocalDateTime requestedDeliveryDate, UserId customerId) {
        Order order = new Order(orderType, requestedDeliveryDate);
        order.setDeliveryAddress(deliveryAddress);
        order.setCustomerId(customerId);
        return order;
    }

    // B2B orders (SweetCrust order bij andere SweetCrust)
    public static Order createB2B(OrderType orderType, LocalDateTime requestedDeliveryDate, ShopId orderingShopId, ShopId sourceShopId) {
        Order order = new Order(orderType, requestedDeliveryDate);
        order.setOrderingShopId(orderingShopId);
        order.setSourceShopId(sourceShopId);
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

    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
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

    public void addOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderDomainException("orderItem", "orderItem should not be null");
        }
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderDomainException("orderItem", "orderItem should not be null");
        }
        orderItems.remove(orderItem);
    }

    public void validateOrder() {
        if (orderType == OrderType.B2C && customerId == null) {
            throw new OrderDomainException("customerId", "B2C orders must have customerId");
        }
        if (orderType == OrderType.B2B && orderingShopId == null) {
            throw new OrderDomainException("orderingShopId", "B2B orders must have orderingShopId");
        }
        if (orderItems.isEmpty()) {
            throw new OrderDomainException("orderItems", "orderItems should not be empty");
        }
    }

    public BigDecimal calculateTotalPrice() {
        return orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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