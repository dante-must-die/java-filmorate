package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс для управления хранилищем фильмов.
 * Определяет основные операции для добавления, обновления, получения и удаления фильмов.
 */


public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Optional<Film> getFilmById(int id);

    List<Film> getAllFilms();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    int getLikesCount(int filmId);

    Set<Integer> getLikesByFilmId(int filmId);
}
