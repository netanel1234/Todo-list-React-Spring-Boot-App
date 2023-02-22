package com.netanel.todolistserver.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user with the given id. Given id: " + id);
    }
}
