package com.jjerome.clearsolutionstestapp.service;

import com.jjerome.clearsolutionstestapp.domain.User;
import com.jjerome.clearsolutionstestapp.dto.UserCreateDto;
import com.jjerome.clearsolutionstestapp.dto.UserPreviewDto;
import com.jjerome.clearsolutionstestapp.dto.UserUpdateDto;
import com.jjerome.clearsolutionstestapp.dto.request.EmailUpdateRequest;
import com.jjerome.clearsolutionstestapp.dto.request.UserSearchRequest;
import com.jjerome.clearsolutionstestapp.exceptions.UserNotFoundException;
import com.jjerome.clearsolutionstestapp.maper.UserMapper;
import com.jjerome.clearsolutionstestapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper usersMapper;

    public User create(UserCreateDto userCreateDto) {
        return userRepository.save(usersMapper.toUser(userCreateDto));
    }

    public User update(String id, UserUpdateDto updateDto) {
        User user = findById(id);
        usersMapper.userUpdateDtoToUser(updateDto, user);
        return userRepository.save(user);
    }

    public void updateEmail(String id, EmailUpdateRequest email) {
        User user = findById(id);
        user.setEmail(email.getEmail());
        userRepository.save(user);
    }

    public void delete(String id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserPreviewDto findPreviewById(String id) {
        User user = findById(id);
        return usersMapper.toPreviewDto(user);
    }

    public List<UserPreviewDto> findByBirthdayRange(UserSearchRequest userSearchRequest) {
        List<User> users = userRepository.findAllByBirthdayBetween(userSearchRequest.getFrom(), userSearchRequest.getTo());
        return usersMapper.toPreviewDtos(users);
    }
}
