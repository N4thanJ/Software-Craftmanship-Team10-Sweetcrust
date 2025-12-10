package com.sweetcrust.team10_bakery.inventory.application;

import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryItemQueryHandler {
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemQueryHandler(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }
}
