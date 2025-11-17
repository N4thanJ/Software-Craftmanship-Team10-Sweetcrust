package com.sweetcrust.team10_bakery.inventory.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.sweetcrust.team10_bakery.inventory.domain.InventoryDomainException;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventories")
public class Inventory {
    @EmbeddedId
    private InventoryId inventoryId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "shop_id"))
    private ShopId shopId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inventory_id")
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    protected Inventory() {
    }

    public Inventory(ShopId shopId) {
        this.inventoryId = new InventoryId();
        setShopId(shopId);
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public ShopId getShopId() {
        return shopId;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryId(InventoryId inventoryId) {
        if (inventoryId == null) {
            throw new InventoryDomainException("InventoryId", "InventoryId should not be null");
        }
        this.inventoryId = inventoryId;
    }

    public void setShopId(ShopId shopId) {
        if (shopId == null) {
            throw new InventoryDomainException("ShopId", "ShopId should not be null");
        }
        this.shopId = shopId;
    }

    public void addInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem == null) {
            throw new InventoryDomainException("InventoryItem", "InventoryItem should not be null");
        }
        inventoryItem.setInventory(this);
        this.inventoryItems.add(inventoryItem);
    }
}
