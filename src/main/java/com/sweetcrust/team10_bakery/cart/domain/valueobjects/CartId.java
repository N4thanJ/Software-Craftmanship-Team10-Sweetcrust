package com.sweetcrust.team10_bakery.cart.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record CartId(UUID id) {

    public CartId {
        Assert.notNull(id, "id must not be null");
    }

    public CartId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public CartId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
