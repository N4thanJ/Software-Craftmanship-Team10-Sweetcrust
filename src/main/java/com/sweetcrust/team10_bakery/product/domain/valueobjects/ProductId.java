package com.sweetcrust.team10_bakery.product.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ProductId(UUID id) {

    public ProductId {
        Assert.notNull(id, "Product id should not be null");
    }

    public ProductId() {
        this(UUID.randomUUID());
    }
}
