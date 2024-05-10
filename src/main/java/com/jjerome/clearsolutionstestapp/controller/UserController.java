package com.jjerome.clearsolutionstestapp.controller;

import com.jjerome.clearsolutionstestapp.domain.User;
import com.jjerome.clearsolutionstestapp.dto.UserPreviewDto;
import com.jjerome.clearsolutionstestapp.dto.UserUpdateDto;
import com.jjerome.clearsolutionstestapp.dto.UserCreateDto;
import com.jjerome.clearsolutionstestapp.dto.request.EmailUpdateRequest;
import com.jjerome.clearsolutionstestapp.dto.request.UserSearchRequest;
import com.jjerome.clearsolutionstestapp.service.UserService;
import com.jjerome.clearsolutionstestapp.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @InitBinder({"userCreateDto", "userUpdateDto"})
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @GetMapping
    public List<UserPreviewDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserPreviewDto getUser(@PathVariable String id) {
        return userService.findPreviewById(id);
    }

    @PostMapping
    public UserPreviewDto create(@Valid @RequestBody UserCreateDto user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id, @Valid @RequestBody UserUpdateDto user) {
        return userService.update(id, user);
    }

    @PatchMapping("/{id}")
    public void patch(@PathVariable String id, @Valid @RequestBody EmailUpdateRequest emailUpdateRequest) {
        userService.updateEmail(id, emailUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @PostMapping("/list")
    public List<UserPreviewDto> findByBirthdayRange(@Valid @RequestBody UserSearchRequest userSearchRequest) {
        return userService.findByBirthdayRange(userSearchRequest);
    }
}
