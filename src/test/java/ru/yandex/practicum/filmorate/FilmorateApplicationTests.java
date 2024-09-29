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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmoRateApplicationTests {

	@Autowired
	@Qualifier("userDbStorage")
	private UserStorage userStorage;

	@Autowired
	private JdbcTemplate jdbcTemplate; // Инъекция JdbcTemplate

	private User testUser;

	@BeforeEach
	void setUp() {
		// Очистка таблицы пользователей перед каждым тестом
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
	public void testFindUserById() {
		Optional<User> userOptional = userStorage.getUserById(testUser.getId());

		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user.getId()).isEqualTo(testUser.getId())
				);
	}
}
