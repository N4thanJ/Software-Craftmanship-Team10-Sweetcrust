package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ShopProductVariantId(UUID id) {

    public ShopProductVariantId {
        Assert.notNull(id, "Shop product variant id should not be null");
    }

    public ShopProductVariantId() {
        this(UUID.randomUUID());
    }
}
