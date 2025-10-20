package com.sweetcrust.team10_bakery.order.domain;

public class OrderDomainException extends RuntimeException {

    private final String field;

    public OrderDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String  getField() {
        return this.field;
    }
}
