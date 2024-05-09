package com.jjerome.clearsolutionstestapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSearchRequest {
    @NotNull
    private Date from;
    @NotNull
    private Date to;
}
