package com.sweetcrust.team10_bakery.product.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record CategoryId(UUID id) {

    public CategoryId {
        Assert.notNull(id, "Category id should not be null");
    }

    public CategoryId() {
        this(UUID.randomUUID());
    }
}
