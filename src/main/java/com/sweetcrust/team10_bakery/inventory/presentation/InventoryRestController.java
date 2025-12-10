package com.sweetcrust.team10_bakery.inventory.presentation;

import com.sweetcrust.team10_bakery.inventory.application.InventoryItemCommandHandler;
import com.sweetcrust.team10_bakery.inventory.application.InventoryItemQueryHandler;
import com.sweetcrust.team10_bakery.inventory.application.commands.AddStockCommand;
import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Management", description = "Endpoints related to inventory management")
public class InventoryRestController {
  private final InventoryItemCommandHandler inventoryItemCommandHandler;
  private final InventoryItemQueryHandler inventoryItemQueryHandler;

  public InventoryRestController(
      InventoryItemCommandHandler inventoryItemCommandHandler,
      InventoryItemQueryHandler inventoryQueryHandler) {
    this.inventoryItemCommandHandler = inventoryItemCommandHandler;
    this.inventoryItemQueryHandler = inventoryQueryHandler;
  }

  @GetMapping()
  public ResponseEntity<Iterable<InventoryItem>> getAllInventoryItems() {
    List<InventoryItem> inventoryItems = inventoryItemQueryHandler.getAllInventoryItems();
    return ResponseEntity.ok(inventoryItems);
  }

  @PostMapping("/{shopId}")
  public ResponseEntity<InventoryItem> addStockToShop(
      @PathVariable ShopId shopId, @RequestBody AddStockCommand addStockCommand) {
    InventoryItem inventoryItem =
        inventoryItemCommandHandler.createInventoryItem(shopId, addStockCommand);
    return ResponseEntity.ok(inventoryItem);
  }
}
