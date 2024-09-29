package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validator.UserValidator.validateUser;

/**
 * Сервисный класс для работы с пользователями и их друзьями.
 * Реализует бизнес-логику для создания, обновления, получения пользователей,
 * а также для управления списками друзей пользователей.
 */


@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        validateUser(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (!userStorage.getUserById(user.getId()).isPresent()) { // проверка существуют ли пользователь
            throw new InvalidIdException("неккоректный id");
        }
        return userStorage.updateUser(user);
    }

    public Optional<User> getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    // методы для работы с друзьями
    public void addFriend(int userId, int friendId) throws NotFoundException {
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        User friend = userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + friendId + " не найден."));
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) throws NotFoundException {
        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        User friend = userStorage.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + friendId + " не найден."));
        userStorage.removeFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) throws NotFoundException {
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        Set<Integer> friendIds = userStorage.getFriendsByUserId(userId);
        return friendIds.stream()
                .map(id -> userStorage.getUserById(id)
                        .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден.")))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherUserId) throws NotFoundException {
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));
        userStorage.getUserById(otherUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + otherUserId + " не найден."));
        Set<Integer> userFriends = userStorage.getFriendsByUserId(userId);
        Set<Integer> otherUserFriends = userStorage.getFriendsByUserId(otherUserId);
        userFriends.retainAll(otherUserFriends);
        return userFriends.stream()
                .map(id -> userStorage.getUserById(id)
                        .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден.")))
                .collect(Collectors.toList());
    }
}
