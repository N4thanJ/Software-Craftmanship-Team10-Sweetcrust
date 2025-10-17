package be.ucll.team10_bakery.order.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import be.ucll.team10_bakery.order.domain.valueobjects.OrderId;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @EmbeddedId
    private OrderId orderId = new OrderId();

    private LocalDate date;

    protected Order() {
    }

    public Order(LocalDate date) {
        setDate(date);
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
