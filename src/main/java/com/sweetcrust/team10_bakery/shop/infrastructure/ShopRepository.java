package com.sweetcrust.team10_bakery.shop.infrastructure;

import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, ShopId> {
  boolean existsByName(String name);
}
