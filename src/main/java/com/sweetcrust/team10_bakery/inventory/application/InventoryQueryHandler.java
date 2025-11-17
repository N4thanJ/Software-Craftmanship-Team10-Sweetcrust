package com.sweetcrust.team10_bakery.inventory.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.inventory.domain.entities.Inventory;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryRepository;

@Service
public class InventoryQueryHandler {
    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryQueryHandler(InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
}
