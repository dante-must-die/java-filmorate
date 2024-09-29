package ru.yandex.practicum.filmorate.model;

/**
 * Модель для представления ошибок в ответах API.
 * Содержит текст ошибки.
 */


public class CustomErrorResponse {
    private final String error;

    public CustomErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
