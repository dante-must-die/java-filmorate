package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для управления хранилищем пользователей.
 * Определяет основные операции для добавления, обновления, получения
 * пользователей из хранилища.
 */
public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(int id);

    List<User> getAllUsers();
}
