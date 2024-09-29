package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель, представляющая фильм.
 * Включает информацию о фильме, такую как название, описание, дата релиза и продолжительность,
 * а также методы для работы с лайками пользователей.
 */

@Data
public class Film {
    @Getter
    private final Set<Integer> likes = new HashSet<>(); // используем set так как один пользователь = один лайк
    private Integer id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Описание не может превышать 200 символов")
    private String description;
    @NotNull(message = "Дата релиза не может быть пустой")
    @PastOrPresent(message = "Дата релиза не может быть в будущем")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private int duration;

    // методы для работы с лайками
    public void addLike(Integer userId) {
        likes.add(userId);
    }

    public void removeLike(Integer userId) {
        likes.remove(userId);
    }

    public int getLikesCount() {
        return likes.size();
    }
}
