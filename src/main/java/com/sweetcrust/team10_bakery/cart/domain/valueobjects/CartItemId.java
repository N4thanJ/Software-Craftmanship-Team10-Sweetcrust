package com.sweetcrust.team10_bakery.cart.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record CartItemId(UUID id) {

    public CartItemId {
        Assert.notNull(id, "id must not be null");
    }

    public CartItemId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public CartItemId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
