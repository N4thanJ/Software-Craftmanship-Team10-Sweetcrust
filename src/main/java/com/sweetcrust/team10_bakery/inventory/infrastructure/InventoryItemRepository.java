package com.sweetcrust.team10_bakery.inventory.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryItemId;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, InventoryItemId> {

}
