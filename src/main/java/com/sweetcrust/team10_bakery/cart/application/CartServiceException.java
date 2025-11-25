package com.sweetcrust.team10_bakery.cart.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class CartServiceException extends ServiceException {

  public CartServiceException(String field, String message) {
    super(field, message);
  }
}
