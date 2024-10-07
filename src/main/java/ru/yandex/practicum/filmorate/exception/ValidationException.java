package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое при ошибках валидации данных.
 */


public class ValidationException extends RuntimeException { // исключение выбрасываемое с ошибками валидации
    public ValidationException(String message) {
        super(message);
    }
}
