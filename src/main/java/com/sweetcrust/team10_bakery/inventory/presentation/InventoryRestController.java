package com.sweetcrust.team10_bakery.inventory.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweetcrust.team10_bakery.inventory.application.InventoryItemCommandHandler;
import com.sweetcrust.team10_bakery.inventory.application.InventoryItemQueryHandler;
import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Management", description = "Endpoints related to inventory management")
public class InventoryRestController {
    private final InventoryItemCommandHandler inventoryItemCommandHandler;
    private final InventoryItemQueryHandler inventoryItemQueryHandler;

    public InventoryRestController(InventoryItemCommandHandler inventoryItemCommandHandler,
            InventoryItemQueryHandler inventoryQueryHandler) {
        this.inventoryItemCommandHandler = inventoryItemCommandHandler;
        this.inventoryItemQueryHandler = inventoryQueryHandler;
    }

    @GetMapping()
    public ResponseEntity<Iterable<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemQueryHandler.getAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }

}
