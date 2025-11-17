package com.sweetcrust.team10_bakery.inventory.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweetcrust.team10_bakery.inventory.application.InventoryCommandHandler;
import com.sweetcrust.team10_bakery.inventory.application.InventoryQueryHandler;
import com.sweetcrust.team10_bakery.inventory.domain.entities.Inventory;
import com.sweetcrust.team10_bakery.product.presentation.dto.ProductResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Management", description = "Endpoints related to inventory management")
public class InventoryRestController {
    private final InventoryCommandHandler inventoryCommandHandler;
    private final InventoryQueryHandler inventoryQueryHandler;

    public InventoryRestController(InventoryCommandHandler inventoryCommandHandler,
            InventoryQueryHandler inventoryQueryHandler) {
        this.inventoryCommandHandler = inventoryCommandHandler;
        this.inventoryQueryHandler = inventoryQueryHandler;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryQueryHandler.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

}
