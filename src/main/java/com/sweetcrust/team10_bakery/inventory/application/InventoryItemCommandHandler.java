package com.sweetcrust.team10_bakery.inventory.application;

import com.sweetcrust.team10_bakery.inventory.application.commands.AddStockCommand;
import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryItemCommandHandler {
    private final InventoryItemRepository inventoryItemRepository;
    private final ShopRepository shopRepository;

    public InventoryItemCommandHandler(InventoryItemRepository inventoryItemRepository, ShopRepository shopRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.shopRepository = shopRepository;
    }

    public InventoryItem createInventoryItem(ShopId shopId, AddStockCommand addStockCommand) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new InventoryItemServiceException("variantId", "Variant not found"));

        boolean foundInventoryItem = inventoryItemRepository.existsByShopIdAndVariantId(shop.getShopId(),
                addStockCommand.variantId());

        if (foundInventoryItem) {
            throw new InventoryItemServiceException("InventoryItem", "This inventoryItem already exists in this shop");
        }

        InventoryItem inventoryItem = new InventoryItem(shop.getShopId(), addStockCommand.variantId());

        return inventoryItemRepository.save(inventoryItem);
    }
}
