package com.jjerome.clearsolutionstestapp.maper;

import com.jjerome.clearsolutionstestapp.domain.User;
import com.jjerome.clearsolutionstestapp.dto.UserCreateDto;
import com.jjerome.clearsolutionstestapp.dto.UserPreviewDto;
import com.jjerome.clearsolutionstestapp.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDto userDto);

    @Mapping(target = "email", ignore = true)
    void userUpdateDtoToUser(UserUpdateDto userUpdateDto, @MappingTarget User user);

    List<UserPreviewDto> toPreviewDtos(List<User> users);

    UserPreviewDto toPreviewDto(User user);
}
