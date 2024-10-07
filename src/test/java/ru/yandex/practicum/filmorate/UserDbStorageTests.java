package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDbStorageTests {

    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Очистка таблицы пользователей и дружбы перед каждым тестом
        jdbcTemplate.update("DELETE FROM friendship");
        jdbcTemplate.update("DELETE FROM users");

        // Создание и сохранение тестового пользователя
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        testUser = userStorage.addUser(user);
    }

    @Test
    void testAddUser() {
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setLogin("newuser");
        newUser.setName("New User");
        newUser.setBirthday(LocalDate.of(1995, 5, 15));
        User createdUser = userStorage.addUser(newUser);

        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo("new@example.com");
        assertThat(createdUser.getLogin()).isEqualTo("newuser");
        assertThat(createdUser.getName()).isEqualTo("New User");
        assertThat(createdUser.getBirthday()).isEqualTo(LocalDate.of(1995, 5, 15));
    }

    @Test
    void testUpdateUser() {
        testUser.setName("Updated Name");
        User updatedUser = userStorage.updateUser(testUser);

        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
    }

    @Test
    void testGetUserById() {
        Optional<User> userOptional = userStorage.getUserById(testUser.getId());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user.getId()).isEqualTo(testUser.getId())
                );
    }

    @Test
    void testGetAllUsers() {
        User anotherUser = new User();
        anotherUser.setEmail("another@example.com");
        anotherUser.setLogin("anotheruser");
        anotherUser.setName("Another User");
        anotherUser.setBirthday(LocalDate.of(1985, 3, 20));
        userStorage.addUser(anotherUser);

        assertThat(userStorage.getAllUsers()).hasSize(2);
    }

    @Test
    void testAddAndRemoveFriend() {
        User friendUser = new User();
        friendUser.setEmail("friend@example.com");
        friendUser.setLogin("frienduser");
        friendUser.setName("Friend User");
        friendUser.setBirthday(LocalDate.of(1992, 7, 10));
        friendUser = userStorage.addUser(friendUser);

        // Добавляем в друзья
        userStorage.addFriend(testUser.getId(), friendUser.getId());
        Set<Integer> friends = userStorage.getFriendsByUserId(testUser.getId());
        assertThat(friends).contains(friendUser.getId());

        // Проверяем, что у friendUser нет testUser в списке друзей (односторонняя дружба)
        Set<Integer> friendsOfFriend = userStorage.getFriendsByUserId(friendUser.getId());
        assertThat(friendsOfFriend).doesNotContain(testUser.getId());

        userStorage.removeFriend(testUser.getId(), friendUser.getId());
        friends = userStorage.getFriendsByUserId(testUser.getId());
        assertThat(friends).doesNotContain(friendUser.getId());
    }

}
