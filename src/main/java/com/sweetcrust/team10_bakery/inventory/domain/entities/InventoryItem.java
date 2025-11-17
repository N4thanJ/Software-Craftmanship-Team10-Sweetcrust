package com.sweetcrust.team10_bakery.inventory.domain.entities;

import com.sweetcrust.team10_bakery.inventory.domain.InventoryDomainException;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryItemId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
    @EmbeddedId
    private InventoryItemId inventoryItemId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    private int quantityOnHand;
    private int quantityReserved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false, insertable = false, updatable = false)
    private Inventory inventory;

    protected InventoryItem() {
    }

    public InventoryItem(ProductId productId, int quantityOnHand, int quantityReserved) {
        this.inventoryItemId = new InventoryItemId();
        setProductId(productId);
        setQuantityOnHand(quantityOnHand);
        setQuantityReserved(quantityReserved);
    }

    public void setInventoryItemId(InventoryItemId inventoryItemId) {
        if (inventoryItemId == null) {
            throw new InventoryDomainException("InventoryItemId", "InventoryItemId should not be null");
        }
        this.inventoryItemId = inventoryItemId;
    }

    public void setProductId(ProductId productId) {
        if (productId == null) {
            throw new InventoryDomainException("ProductId", "ProductId should not be null");
        }
        this.productId = productId;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        if (quantityOnHand < 0) {
            throw new InventoryDomainException("QuantityOnHand", "QuantityOnHand should be a postive number");
        }
        this.quantityOnHand = quantityOnHand;
    }

    public void setQuantityReserved(int quantityReserved) {
        if (quantityReserved < 0) {
            throw new InventoryDomainException("QuantityReserved", "QuantityReserved should be a postive number");
        }
        this.quantityReserved = quantityReserved;
    }

    public void setInventory(Inventory inventory) {
        if (inventory == null) {
            throw new InventoryDomainException("Inventory", "Inventory should not be null");
        }
        this.inventory = inventory;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public int getQuantityReserved() {
        return quantityReserved;
    }

    public InventoryItemId getInventoryItemId() {
        return inventoryItemId;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
