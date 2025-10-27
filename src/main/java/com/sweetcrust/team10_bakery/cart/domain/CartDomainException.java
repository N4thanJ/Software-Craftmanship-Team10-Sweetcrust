package com.sweetcrust.team10_bakery.cart.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class CartDomainException extends DomainException {

    public CartDomainException(String field, String message) {
        super(field, message);
    }
}
