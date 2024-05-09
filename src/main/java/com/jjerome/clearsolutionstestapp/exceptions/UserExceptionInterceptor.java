package com.jjerome.clearsolutionstestapp.exceptions;

import com.jjerome.clearsolutionstestapp.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionInterceptor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseDto handleUserNotFoundException(UserNotFoundException ex) {
        return ErrorResponseDto.of(ex.getMessage());
    }
}
