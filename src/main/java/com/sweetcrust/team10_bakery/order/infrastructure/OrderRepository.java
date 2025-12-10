package com.sweetcrust.team10_bakery.order.infrastructure;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
  List<Order> findBySourceShopId(ShopId sourceShopId);

  List<Order> findByStatusAndRequestedDeliveryDateBetween(
      OrderStatus status, LocalDateTime startInclusive, LocalDateTime endInclusive);

  List<Order> findByCustomerId(UserId customerId);
}
