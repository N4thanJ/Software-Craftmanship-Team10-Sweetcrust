package com.sweetcrust.team10_bakery.user.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class UserDomainException extends DomainException {

  public UserDomainException(String field, String message) {
    super(field, message);
  }
}
