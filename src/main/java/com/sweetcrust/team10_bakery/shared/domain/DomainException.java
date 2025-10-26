package com.sweetcrust.team10_bakery.shared.domain;

public abstract class DomainException extends RuntimeException {

    private final String field;

    public DomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String  getField() {
        return this.field;
    }
}
