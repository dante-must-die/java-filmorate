package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель, представляющая рейтинг MPA для фильмов.
 * Включает идентификатор рейтинга и его название.
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpaRating {
    private int id;
    private String name;
}

