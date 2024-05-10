package com.jjerome.clearsolutionstestapp.validator;


import com.jjerome.clearsolutionstestapp.dto.BaseUserDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UserValidator implements Validator {
    @Value("${user.validation.migAge:18}")
    private int minAge;

    @Override
    public boolean supports(Class<?> clazz) {
        return BaseUserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BaseUserDto userDto = (BaseUserDto) target;

        validateBirthday(userDto.getBirthday(), errors);
    }

    private void validateBirthday(@NotNull Date birthday, Errors errors){
        if (birthday == null){
            errors.rejectValue("birthday", "error.birthday", "birthday is empty");
        }

        LocalDate birthDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        if (period.getYears() < minAge){
//            throw new UserIsOverAgeLimitException();
            errors.rejectValue("birthday", "user.validation.birthday", "Your minimal age is too low");
        }
    }
}
