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
 * Сервисный класс для работы с фильмами.
 * Реализует бизнес-логику для создания, обновления, получения и управления фильмами,
 * а также для работы с лайками фильмов и их популярностью.
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

    public Film addFilm(Film film) {
        validateFilm(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        if (!filmStorage.getFilmById(film.getId()).isPresent()) {
            throw new InvalidIdException("Некорректный ID");
        }
        return filmStorage.updateFilm(film);
    }

    public Optional<Film> getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(int filmId, int userId) {
        // Проверяем, существует ли фильм
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с ID " + filmId + " не найден."));

        // Проверяем, существует ли пользователь
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        // Добавляем лайк через filmStorage
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        // Проверяем, существует ли фильм
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с ID " + filmId + " не найден."));

        // Проверяем, существует ли пользователь
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден."));

        // Удаляем лайк через filmStorage
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> Integer.compare(
                        filmStorage.getLikesCount(f2.getId()),
                        filmStorage.getLikesCount(f1.getId())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
