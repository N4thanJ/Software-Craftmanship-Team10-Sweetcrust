package com.sweetcrust.team10_bakery.order.infrastructure;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderStatus;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
  List<Order> findBySourceShopId(ShopId sourceShopId);


  List<Order> findByStatusAndRequestedDeliveryDateBetween(OrderStatus status, LocalDateTime startInclusive, LocalDateTime endInclusive);
}
