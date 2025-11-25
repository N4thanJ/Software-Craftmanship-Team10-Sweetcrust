package com.sweetcrust.team10_bakery.shared.domain;

public class SharedDomainException extends DomainException {

  public SharedDomainException(String field, String message) {
    super(field, message);
  }
}
