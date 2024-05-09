package com.jjerome.clearsolutionstestapp.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userID){
        super("User with ID %s not found".formatted(userID));
    }

    public UserNotFoundException() {
        super("User not found");
    }
}
