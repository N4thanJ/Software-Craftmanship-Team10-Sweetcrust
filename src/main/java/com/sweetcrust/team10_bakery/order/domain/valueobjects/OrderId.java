package com.sweetcrust.team10_bakery.order.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record OrderId(UUID id) {

  public OrderId {
    Assert.notNull(id, "id must not be null");
  }

  public OrderId() {
    this(UUID.randomUUID());
  }

  @JsonCreator
  public OrderId(String id) {
    this(UUID.fromString(id));
  }

  @JsonValue
  public String asString() {
    return id.toString();
  }
}
