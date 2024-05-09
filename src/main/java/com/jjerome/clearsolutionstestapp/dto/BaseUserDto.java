package com.jjerome.clearsolutionstestapp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseUserDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotNull
    private Date birthday;
}

