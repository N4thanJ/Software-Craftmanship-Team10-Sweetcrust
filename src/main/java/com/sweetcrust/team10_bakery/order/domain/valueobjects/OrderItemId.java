package com.sweetcrust.team10_bakery.order.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record OrderItemId(UUID id) {

    public OrderItemId {
        Assert.notNull(id, "id must not be null");
    }

    public OrderItemId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public OrderItemId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
