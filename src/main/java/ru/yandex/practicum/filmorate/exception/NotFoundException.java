package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое, если объект не найден.
 */


public class NotFoundException extends RuntimeException { // исключение если обьект не найден
    public NotFoundException(String message) {
        super(message);
    }
}
