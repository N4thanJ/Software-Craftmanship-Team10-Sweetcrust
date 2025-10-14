package be.ucll.team10_bakery.order.infrastructure;

import be.ucll.team10_bakery.order.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
