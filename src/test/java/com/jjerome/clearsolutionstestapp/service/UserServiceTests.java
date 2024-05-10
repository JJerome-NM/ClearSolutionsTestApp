package com.jjerome.clearsolutionstestapp.service;

import com.jjerome.clearsolutionstestapp.domain.User;
import com.jjerome.clearsolutionstestapp.dto.UserCreateDto;
import com.jjerome.clearsolutionstestapp.dto.UserPreviewDto;
import com.jjerome.clearsolutionstestapp.dto.UserUpdateDto;
import com.jjerome.clearsolutionstestapp.exceptions.UserNotFoundException;
import com.jjerome.clearsolutionstestapp.maper.UserMapper;
import com.jjerome.clearsolutionstestapp.validator.UserValidator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureDataMongo
public class UserServiceTests {
    private static List<User> users = generateUsers(5);
    private static ValidatorFactory validatorFactory;
    private static Validator validation;

    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;
    private final UserValidator userValidator = new UserValidator();


    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validation = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @BeforeEach
    void setupMocks() {
        mongoTemplate.dropCollection(User.class);

        mongoTemplate.insertAll(users);
    }

    private static List<User> generateUsers(int size) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            users.add(User.builder()
                    .id("%s".formatted(i))
                    .email("test%s@gmail.com".formatted(i))
                    .name("test%s".formatted(i))
                    .lastname("lastnameTest%s".formatted(i))
                    .birthday(new Date(2022, Calendar.JANUARY, 1))
                    .address("addressTest%s".formatted(i))
                    .phone("phoneTest%s".formatted(i))
                    .build()
            );
        }
        return users;
    }

    @Test
    void findAllUsers_Test() {
        List<UserPreviewDto> dtos = userService.findAll();

        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        assertEquals(dtos.size(), users.size());

        for (int i = 0; i < dtos.size(); ++i) {
            UserPreviewDto dto = dtos.get(i);
            User user = users.get(i);

            assertEquals(dto.getId(), user.getId());
            assertEquals(dto.getEmail(), user.getEmail());
            assertEquals(dto.getName(), user.getName());
            assertEquals(dto.getLastname(), user.getLastname());
            assertEquals(dto.getBirthday(), user.getBirthday());
        }
    }

    @Test
    void findUserById_Test() {
        User user = users.getFirst();

        UserPreviewDto dto = userService.findPreviewById(user.getId());

        assertNotNull(dto);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getLastname(), user.getLastname());
        assertEquals(dto.getBirthday(), user.getBirthday());
    }

    @Test
    void findNotExistUserById_Test() {
        assertThrows(UserNotFoundException.class, () -> userService.findPreviewById("unknownId"));
    }

    @Test
    void createUserValidation_Test(){
        UserCreateDto createDto = new UserCreateDto();
        createDto.setEmail("");
        createDto.setName("test1");
        createDto.setLastname("lastnametest");
        createDto.setBirthday(new Date(102, Calendar.FEBRUARY, 1));

        var res = validation.validate(createDto);
        assertTrue(res.isEmpty());

        Errors errors = new BeanPropertyBindingResult(createDto, "createDto");
        userValidator.validate(createDto, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void createUser_Test() {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setEmail("test@gmail.com");
        createDto.setName("test1");
        createDto.setLastname("lastnametest");
        createDto.setBirthday(new Date(105, Calendar.FEBRUARY, 1));

        UserPreviewDto result = userService.create(createDto);

        assertNotNull(result);
        assertEquals(createDto.getEmail(), result.getEmail());
        assertEquals(createDto.getName(), result.getName());
        assertEquals(createDto.getLastname(), result.getLastname());
        assertEquals(createDto.getBirthday(), result.getBirthday());
    }

    @Test
    void deleteUser_Test() {
        User user = users.getFirst();

        userService.delete(user.getId());

        assertThrows(UserNotFoundException.class, () -> userService.findPreviewById(user.getId()));
    }

    @Test
    void updateUser_Test() {
        User user = users.getFirst();
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setName("SuperUser");

        User result = userService.update(user.getId(), updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getName(), result.getName());
    }

    @Test
    void updateAllFieldsUser_Test2() {
        User user = users.getFirst();
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setName("SuperUser");
        updateDto.setLastname("SuperLastname");
        updateDto.setBirthday(new Date(102, Calendar.FEBRUARY, 1));
        updateDto.setAddress("SuperAddress");
        updateDto.setPhone("SuperPhone");

        User result = userService.update(user.getId(), updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getName(), result.getName());
        assertEquals(updateDto.getLastname(), result.getLastname());
        assertEquals(updateDto.getBirthday(), result.getBirthday());
        assertEquals(updateDto.getAddress(), result.getAddress());
        assertEquals(updateDto.getPhone(), result.getPhone());
    }
}
