package com.sweetcrust.team10_bakery.user.domain.valueobjects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record UserId(UUID id) {

    public UserId {
        Assert.notNull(id, "id must not be null");
    }

    public UserId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public UserId(String id) {
        this(UUID.fromString(id));
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }
}
