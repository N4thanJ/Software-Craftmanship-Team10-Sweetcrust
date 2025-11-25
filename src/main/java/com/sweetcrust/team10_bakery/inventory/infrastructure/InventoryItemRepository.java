package com.sweetcrust.team10_bakery.inventory.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryItemId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, InventoryItemId> {

  boolean existsByShopIdAndVariantId(ShopId shopId, VariantId variantId);
}
