package com.sweetcrust.team10_bakery.order.application;

public class OrderServiceException extends RuntimeException {

    private final String field;

    public OrderServiceException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
