package com.jjerome.clearsolutionstestapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto extends BaseUserDto {
    private String address;
    private String phone;
}
