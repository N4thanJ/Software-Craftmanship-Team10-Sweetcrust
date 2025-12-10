package com.sweetcrust.team10_bakery.inventory.domain.entities;

import com.sweetcrust.team10_bakery.inventory.domain.InventoryDomainException;
import com.sweetcrust.team10_bakery.inventory.domain.valueobjects.InventoryItemId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
  @EmbeddedId private InventoryItemId inventoryItemId;

  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "shop_id"))
  private ShopId shopId;

  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "varian_id"))
  private VariantId variantId;

  private int quantityOnHand;
  private int quantityReserved;

  protected InventoryItem() {}

  public InventoryItem(ShopId shopId, VariantId variantId) {
    this.inventoryItemId = new InventoryItemId();
    setShopId(shopId);
    setVariantId(variantId);
    setQuantityOnHand(0);
    setQuantityReserved(0);
  }

  public void setInventoryItemId(InventoryItemId inventoryItemId) {
    if (inventoryItemId == null) {
      throw new InventoryDomainException("InventoryItemId", "InventoryItemId should not be null");
    }
    this.inventoryItemId = inventoryItemId;
  }

  public void setShopId(ShopId shopId) {
    if (shopId == null) {
      throw new InventoryDomainException("ShopId", "ShopId should not be null");
    }
    this.shopId = shopId;
  }

  public void setVariantId(VariantId variantId) {
    if (variantId == null) {
      throw new InventoryDomainException("VariantId", "VariantId should not be null");
    }
    this.variantId = variantId;
  }

  public void setQuantityOnHand(int quantityOnHand) {
    if (quantityOnHand < 0) {
      throw new InventoryDomainException(
          "QuantityOnHand", "QuantityOnHand should be a postive number");
    }
    this.quantityOnHand = quantityOnHand;
  }

  public void setQuantityReserved(int quantityReserved) {
    if (quantityReserved < 0) {
      throw new InventoryDomainException(
          "QuantityReserved", "QuantityReserved should be a postive number");
    }
    this.quantityReserved = quantityReserved;
  }

  public ShopId getShopId() {
    return shopId;
  }

  public VariantId getVariantId() {
    return variantId;
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
}
