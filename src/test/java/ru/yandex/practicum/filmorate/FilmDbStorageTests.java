package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FilmDbStorageTests {

    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Film testFilm;

    @BeforeEach
    void setUp() {
        // Очистка таблиц перед каждым тестом
        jdbcTemplate.update("DELETE FROM film_likes");
        jdbcTemplate.update("DELETE FROM film_genres");
        jdbcTemplate.update("DELETE FROM films");
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("DELETE FROM mpa_ratings");
        jdbcTemplate.update("DELETE FROM genres");

        // Вставка необходимых данных
        jdbcTemplate.update("INSERT INTO mpa_ratings (id, name) VALUES (?, ?)", 1, "G");
        jdbcTemplate.update("INSERT INTO mpa_ratings (id, name) VALUES (?, ?)", 2, "PG");
        jdbcTemplate.update("INSERT INTO mpa_ratings (id, name) VALUES (?, ?)", 3, "PG-13");
        jdbcTemplate.update("INSERT INTO mpa_ratings (id, name) VALUES (?, ?)", 4, "R");
        jdbcTemplate.update("INSERT INTO mpa_ratings (id, name) VALUES (?, ?)", 5, "NC-17");

        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 1, "Комедия");
        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 2, "Драма");
        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 3, "Мультфильм");
        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 4, "Триллер");
        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 5, "Документальный");
        jdbcTemplate.update("INSERT INTO genres (id, name) VALUES (?, ?)", 6, "Боевик");

        jdbcTemplate.update("INSERT INTO users (id, email, login, name, birthday) VALUES (?, ?, ?, ?, ?)",
                1, "test@example.com", "testuser", "Test User", LocalDate.of(1990, 1, 1));

        // Создание и сохранение тестового фильма
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        // Устанавливаем рейтинг MPA
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(1); // Рейтинг с ID 1 теперь существует
        film.setMpa(mpaRating);

        // Добавляем жанры
        Genre genre = new Genre();
        genre.setId(1); // Жанр с ID 1 теперь существует
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        film.setGenres(genres);

        testFilm = filmStorage.addFilm(film);
    }

    @Test
    void testAddFilm() {
        Film newFilm = new Film();
        newFilm.setName("New Film");
        newFilm.setDescription("New Description");
        newFilm.setReleaseDate(LocalDate.of(2010, 5, 15));
        newFilm.setDuration(90);

        // Устанавливаем рейтинг MPA
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(2); // Предполагается, что рейтинг с ID 2 существует в таблице mpa_ratings
        newFilm.setMpa(mpaRating);

        Film createdFilm = filmStorage.addFilm(newFilm);

        assertThat(createdFilm.getId()).isNotNull();
        assertThat(createdFilm.getName()).isEqualTo("New Film");
        assertThat(createdFilm.getDescription()).isEqualTo("New Description");
        assertThat(createdFilm.getReleaseDate()).isEqualTo(LocalDate.of(2010, 5, 15));
        assertThat(createdFilm.getDuration()).isEqualTo(90);
        assertThat(createdFilm.getMpa().getId()).isEqualTo(2);
    }

    @Test
    void testUpdateFilm() {
        testFilm.setName("Updated Film Name");

        // Обновляем рейтинг MPA
        MpaRating newMpaRating = new MpaRating();
        newMpaRating.setId(3); // Предполагается, что рейтинг с ID 3 существует
        testFilm.setMpa(newMpaRating);

        Film updatedFilm = filmStorage.updateFilm(testFilm);

        assertThat(updatedFilm.getName()).isEqualTo("Updated Film Name");
        assertThat(updatedFilm.getMpa().getId()).isEqualTo(3);
    }

    @Test
    void testGetFilmById() {
        Optional<Film> filmOptional = filmStorage.getFilmById(testFilm.getId());

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film -> {
                    assertThat(film.getId()).isEqualTo(testFilm.getId());
                    assertThat(film.getMpa().getId()).isEqualTo(testFilm.getMpa().getId());
                });
    }

    @Test
    void testGetAllFilms() {
        Film anotherFilm = new Film();
        anotherFilm.setName("Another Film");
        anotherFilm.setDescription("Another Description");
        anotherFilm.setReleaseDate(LocalDate.of(2015, 8, 20));
        anotherFilm.setDuration(110);

        // Устанавливаем рейтинг MPA
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(4); // Предполагается, что рейтинг с ID 4 существует
        anotherFilm.setMpa(mpaRating);

        filmStorage.addFilm(anotherFilm);

        assertThat(filmStorage.getAllFilms()).hasSize(2);
    }

    @Test
    void testAddAndRemoveLike() {
        // Предполагается, что пользователь с ID 1 существует
        int userId = 1;

        // Добавляем лайк
        filmStorage.addLike(testFilm.getId(), userId);
        int likesCount = filmStorage.getLikesCount(testFilm.getId());
        assertThat(likesCount).isEqualTo(1);

        // Проверяем, что лайк сохранен
        Set<Integer> likes = filmStorage.getLikesByFilmId(testFilm.getId());
        assertThat(likes).contains(userId);

        // Удаляем лайк
        filmStorage.removeLike(testFilm.getId(), userId);
        likesCount = filmStorage.getLikesCount(testFilm.getId());
        assertThat(likesCount).isEqualTo(0);

        // Проверяем, что лайк удален
        likes = filmStorage.getLikesByFilmId(testFilm.getId());
        assertThat(likes).doesNotContain(userId);
    }
}
