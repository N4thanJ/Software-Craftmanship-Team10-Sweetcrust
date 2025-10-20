package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ShopId(UUID id) {

    public ShopId {
        Assert.notNull(id, "Shop id should not be null");
    }

    public ShopId() {
        this(UUID.randomUUID());
    }
}
