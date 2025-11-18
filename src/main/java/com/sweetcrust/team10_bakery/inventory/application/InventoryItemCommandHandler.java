package com.sweetcrust.team10_bakery.inventory.application;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;

@Service
public class InventoryItemCommandHandler {
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemCommandHandler(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }
}
