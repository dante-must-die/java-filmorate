package ru.yandex.practicum.filmorate;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setLogin("validlogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUserEmail() {
        User user = new User();
        user.setId(1);
        user.setEmail("invalid-email");
        user.setLogin("validlogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Некорректный формат электронной почты", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyUserLogin() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setLogin("");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым", violations.iterator().next().getMessage());
    }

    @Test
    public void testFutureUserBirthday() {
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setLogin("validlogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.of(2100, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем", violations.iterator().next().getMessage());
    }
}
