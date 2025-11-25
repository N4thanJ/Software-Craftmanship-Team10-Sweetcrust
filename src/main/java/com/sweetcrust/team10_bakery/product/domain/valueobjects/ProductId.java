package com.sweetcrust.team10_bakery.product.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record ProductId(UUID id) {

    public ProductId {
        Assert.notNull(id, "Product id should not be null");
    }

    public ProductId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public ProductId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
