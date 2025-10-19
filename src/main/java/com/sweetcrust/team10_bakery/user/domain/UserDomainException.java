package com.sweetcrust.team10_bakery.user.domain;

public class UserDomainException extends RuntimeException {

    private final String field;

    public UserDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return this.field;
    }
}
