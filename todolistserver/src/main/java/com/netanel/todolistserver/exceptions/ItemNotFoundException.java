package com.netanel.todolistserver.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Could not find item with the given id. Given id: " + id);
    }
}
