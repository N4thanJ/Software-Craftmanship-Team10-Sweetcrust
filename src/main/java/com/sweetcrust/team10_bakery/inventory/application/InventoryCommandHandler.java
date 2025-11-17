package com.sweetcrust.team10_bakery.inventory.application;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryRepository;

@Service
public class InventoryCommandHandler {
    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryCommandHandler(InventoryRepository inventoryRepository,
            InventoryItemRepository inventoryItemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }
}
