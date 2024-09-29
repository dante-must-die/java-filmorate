package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;


/**
 * модель для класса user
 * реализует основные поля и методы для работы с друзьями
 */
@Data
public class User {
    private Integer id;
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Некорректный формат электронной почты")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    private String name;
    @NotNull(message = "Дата рождения не может быть пустой")
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public Set<Integer> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = new HashSet<>(friends);
    }
}


