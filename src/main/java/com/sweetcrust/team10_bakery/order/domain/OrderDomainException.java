package com.sweetcrust.team10_bakery.order.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class OrderDomainException extends DomainException {

    public OrderDomainException(String field, String message) {
        super(field, message);
    }
}
