package com.sweetcrust.team10_bakery.inventory.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class InventoryItemServiceException extends ServiceException {

    public InventoryItemServiceException(String field, String message) {
        super(field, message);
    }

}
