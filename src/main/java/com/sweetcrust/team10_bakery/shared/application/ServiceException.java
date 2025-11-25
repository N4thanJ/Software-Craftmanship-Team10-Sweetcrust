package com.sweetcrust.team10_bakery.shared.application;

public abstract class ServiceException extends RuntimeException {
  private final String field;

  public ServiceException(String field, String message) {
    super(message);
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
