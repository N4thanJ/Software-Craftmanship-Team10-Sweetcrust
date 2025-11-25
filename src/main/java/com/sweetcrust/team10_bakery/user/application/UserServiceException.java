package com.sweetcrust.team10_bakery.user.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class UserServiceException extends ServiceException {

  public UserServiceException(String field, String message) {
    super(field, message);
  }
}
