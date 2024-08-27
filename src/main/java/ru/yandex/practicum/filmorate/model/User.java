package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User { // модель user
    @Getter
    private final Set<Integer> friends = new HashSet<>(); // hashset друзей пользователя
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

    // методы для работы с друзьями
    public void addFriend(int id) {
        friends.add(id);
    }

    public void removeFriend(int id) {
        friends.remove(id);
    }

    public List<Integer> getFiends() {
        return new ArrayList<>(friends);
    }
}

