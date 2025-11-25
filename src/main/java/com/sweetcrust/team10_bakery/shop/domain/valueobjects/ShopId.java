package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.springframework.util.Assert;

public record ShopId(UUID id) {

  public ShopId {
    Assert.notNull(id, "Shop id should not be null");
  }

  public ShopId() {
    this(UUID.randomUUID());
  }

  @JsonCreator
  public ShopId(String id) {
    this(UUID.fromString(id));
  }

  @JsonValue
  public String asString() {
    return id.toString();
  }
}
