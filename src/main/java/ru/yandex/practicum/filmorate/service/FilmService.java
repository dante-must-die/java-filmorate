package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validator.FilmValidator.validateFilm;

/**
 * Сервисный класс для работы с фильмами и их рейтингом.
 * Реализует бизнес-логику для управления фильмами.
 */

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) { // метод для добавления фильма
        validateFilm(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) { // метод для обновления фильма с проверкой на null
        if (!filmStorage.getFilmById(film.getId()).isPresent()) {
            throw new InvalidIdException("неккоректный id");
        }
        return filmStorage.updateFilm(film);
    }

    public Optional<Film> getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(int filmId, int userId) { // метод для добавления лайка
        Film film = filmStorage.getFilmById(filmId).orElseThrow(() -> new NotFoundException("Фильм с ID " + filmId + " не найден."));

        // Проверяем, существует ли пользователь
        if (!userStorage.getUserById(userId).isPresent()) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден.");
        }

        film.getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) { // метод для удаления лайка
        Film film = filmStorage.getFilmById(filmId).orElseThrow(() -> new NotFoundException("Фильм с ID " + filmId + " не найден."));
        if (!film.getLikes().contains(userId)) {
            throw new NotFoundException("Пользователь с ID " + userId + "не найден");
        }
        film.getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int count) { // метод для поиска популярных фильмов
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
