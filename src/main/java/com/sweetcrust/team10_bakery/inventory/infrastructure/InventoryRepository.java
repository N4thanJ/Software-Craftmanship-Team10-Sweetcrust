package com.sweetcrust.team10_bakery.inventory.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sweetcrust.team10_bakery.inventory.domain.entities.Inventory;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryId;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {

}
