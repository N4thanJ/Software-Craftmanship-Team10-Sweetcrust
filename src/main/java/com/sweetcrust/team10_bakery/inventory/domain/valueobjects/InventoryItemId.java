package com.sweetcrust.team10_bakery.inventory.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record InventoryItemId(UUID id) {
  public InventoryItemId {
    Assert.notNull(id, "Inventory id should not be null");
  }

  public InventoryItemId() {
    this(UUID.randomUUID());
  }

  @JsonCreator
  public InventoryItemId(String id) {
    this(UUID.fromString(id));
  }

  @JsonValue
  public String asString() {
    return id.toString();
  }
}
