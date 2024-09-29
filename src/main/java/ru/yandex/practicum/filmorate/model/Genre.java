package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель, представляющая жанр фильма.
 * Включает идентификатор жанра и его название.
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private int id;
    private String name;
}
