package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для управления хранилищем рейтингов MPA.
 * Определяет операции получения всех рейтингов и поиска рейтинга по ID.
 */


public interface MpaRatingStorage {
    List<MpaRating> getAllMpaRatings();

    Optional<MpaRating> getMpaRatingById(int id);
}
