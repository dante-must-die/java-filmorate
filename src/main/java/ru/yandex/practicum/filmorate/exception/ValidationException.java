package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException { // исключение выбрасываемое с ошибками валидации
    public ValidationException(String message) {
        super(message);
    }
}
