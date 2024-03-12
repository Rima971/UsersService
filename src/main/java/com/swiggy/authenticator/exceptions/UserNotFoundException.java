package com.swiggy.authenticator.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.swiggy.authenticator.constants.ErrorMessage.USERNAME_NOT_FOUND;

public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException() {
        super(USERNAME_NOT_FOUND);
    }
}
