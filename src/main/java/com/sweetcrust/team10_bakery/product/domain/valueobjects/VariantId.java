package com.sweetcrust.team10_bakery.product.domain.valueobjects;


import org.springframework.util.Assert;

import java.util.UUID;

public record VariantId(UUID id) {

    public VariantId {
        Assert.notNull(id, "Variant id should not be null");
    }

    public VariantId() {
        this(UUID.randomUUID());
    }
}
