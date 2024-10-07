package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для управления хранилищем жанров фильмов.
 * Определяет операции получения всех жанров и поиска жанра по ID.
 */


public interface GenreStorage {
    List<Genre> getAllGenres();

    Optional<Genre> getGenreById(int id);
}
