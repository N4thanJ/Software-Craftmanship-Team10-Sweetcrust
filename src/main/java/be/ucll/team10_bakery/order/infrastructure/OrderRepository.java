package be.ucll.team10_bakery.order.infrastructure;

import be.ucll.team10_bakery.order.domain.entities.Order;
import be.ucll.team10_bakery.order.domain.valueobjects.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
