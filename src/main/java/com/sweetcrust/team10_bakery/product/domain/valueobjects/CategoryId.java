package com.sweetcrust.team10_bakery.product.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record CategoryId(UUID id) {

    public CategoryId {
        Assert.notNull(id, "Category id should not be null");
    }

    public CategoryId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public CategoryId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
