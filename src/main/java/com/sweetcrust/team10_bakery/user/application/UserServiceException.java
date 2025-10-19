package com.sweetcrust.team10_bakery.user.application;

public class UserServiceException extends RuntimeException {

    private final String field;

    public UserServiceException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
