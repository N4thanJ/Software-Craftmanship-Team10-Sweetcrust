package com.sweetcrust.team10_bakery.product.domain.valueobjects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record VariantId(UUID id) {

    public VariantId {
        Assert.notNull(id, "Variant id should not be null");
    }

    public VariantId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public VariantId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
