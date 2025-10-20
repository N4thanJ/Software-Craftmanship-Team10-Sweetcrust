package com.sweetcrust.team10_bakery.product.application;

public class ProductServiceException extends RuntimeException {

    private final String field;

    public ProductServiceException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
