package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record ShopProductVariantId(UUID id) {

    public ShopProductVariantId {
        Assert.notNull(id, "Shop product variant id should not be null");
    }

    public ShopProductVariantId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public ShopProductVariantId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
