package com.sweetcrust.team10_bakery.order.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record OrderItemId(UUID id) {

    public OrderItemId {
        Assert.notNull(id, "id must not be null");
    }

    public OrderItemId() {
        this(UUID.randomUUID());
    }
}
