package com.sweetcrust.team10_bakery.inventory.application;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;

public class InventoryServiceException extends ServiceException {

    public InventoryServiceException(String field, String message) {
        super(field, message);
    }

}
