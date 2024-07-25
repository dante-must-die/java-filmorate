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
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final List<User> users = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validateUser(user);
        users.add(user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        validateUser(updatedUser);
        for (User user : users) {
            if (user.getId() == id) {
                user.setEmail(updatedUser.getEmail());
                user.setLogin(updatedUser.getLogin());
                user.setName(updatedUser.getName() != null ? updatedUser.getName() : updatedUser.getLogin());
                user.setBirthday(updatedUser.getBirthday());
                log.info("Пользователь обновлен: {}", user);
                return user;
            }
        }
        throw new ValidationException("Пользователь с id " + id + " не найден");
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем: {}", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
