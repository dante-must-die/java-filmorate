package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс для управления хранилищем пользователей.
 * Определяет операции добавления, обновления, получения пользователей и работы с их списками друзей.
 */

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(int id);

    List<User> getAllUsers();

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Set<Integer> getFriendsByUserId(int userId);
}
