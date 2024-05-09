package com.jjerome.clearsolutionstestapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ErrorResponseDto {

    private String message;

    public static ErrorResponseDto of(String message) {
        return new ErrorResponseDto(message);
    }
}
