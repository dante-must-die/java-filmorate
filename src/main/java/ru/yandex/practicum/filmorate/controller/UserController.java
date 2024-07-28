package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.UserValidator.validateUser;

@RestController
@RequestMapping("/users")
@Validated
public class UserController { // класс контроллер для запросов users
    private static final Logger log = LoggerFactory.getLogger(UserController.class); // логгер
    private static int generatorId = 0;
    private final Map<Integer, User> users = new HashMap<>(); // основная структура для хранения информации о пользователях

    @PostMapping
    public User createUser(@Valid @RequestBody User user) { // энд-поинт на запрос post
        validateUser(user);
        user.setId(++generatorId);
        users.put(generatorId, user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping() // энд-поинт на запрос put
    public User updateUser(@Valid @RequestBody User updatedUser) {
        validateUser(updatedUser);
        int id = updatedUser.getId();
        if (!users.containsKey(id)) {
            log.debug("Пользователь не найден.");
            throw new ValidationException("Пользователь с id " + id + " не найден");
        }
        users.put(id, updatedUser);
        return updatedUser;
    }

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    } //энд-поинт на запрос put

}
