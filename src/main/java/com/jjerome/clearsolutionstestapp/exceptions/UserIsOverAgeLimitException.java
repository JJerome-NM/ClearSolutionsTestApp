package com.jjerome.clearsolutionstestapp.exceptions;

public class UserIsOverAgeLimitException extends RuntimeException {

    public UserIsOverAgeLimitException(String message) {
        super(message);
    }

    public UserIsOverAgeLimitException() {
        super("User is over age limit");
    }
}
