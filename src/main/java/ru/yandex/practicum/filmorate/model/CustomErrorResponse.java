package ru.yandex.practicum.filmorate.model;

public class CustomErrorResponse { // класс для ошибок
    private final String error;

    public CustomErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
