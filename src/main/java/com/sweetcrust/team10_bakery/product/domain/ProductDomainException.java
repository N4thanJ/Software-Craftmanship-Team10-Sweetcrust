package com.sweetcrust.team10_bakery.product.domain;

public class ProductDomainException extends RuntimeException {

    private final String field;

    public ProductDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
