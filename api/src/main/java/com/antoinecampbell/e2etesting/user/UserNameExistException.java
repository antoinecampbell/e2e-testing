package com.antoinecampbell.e2etesting.user;

public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String username) {
        super(String.format("The username %s is already taken", username));
    }
}
