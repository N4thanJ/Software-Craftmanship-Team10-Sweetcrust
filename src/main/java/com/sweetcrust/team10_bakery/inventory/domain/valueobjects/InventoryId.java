package com.sweetcrust.team10_bakery.inventory.domain.valueobjects;

import java.util.UUID;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record InventoryId(UUID id) {
    public InventoryId {
        Assert.notNull(id, "Inventory id should not be null");
    }

    public InventoryId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public InventoryId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
