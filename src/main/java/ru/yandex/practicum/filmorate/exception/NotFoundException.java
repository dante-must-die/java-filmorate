package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends RuntimeException { // исключение если обьект не найден
    public NotFoundException(String message) {
        super(message);
    }
}
